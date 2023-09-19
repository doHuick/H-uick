from decouple import config

from langchain.chat_models import ChatOpenAI
from langchain.schema import HumanMessage, SystemMessage

from collections import defaultdict

from ai.app.models.contract_info import ContractInfo

conversation_history = defaultdict(list)

# OpenAI API 키 설정
contracts_model = ChatOpenAI(model_name='gpt-4', temperature=0.8, openai_api_key=config('OPENAI_API_KEY'))
contracts_sys = SystemMessage(content="""
                              Communicate with users in Korean
                              Ask the questions in the following order
                              The user doesn't have to complete the form.
                              When the user answers, send a confirmation message with the next question, such as '차용금액은 1,000,000원으로 설정했습니다.'
                              If the previous answer is incorrect, let them edit it.
                              When you edit, don't go back to the beginning, just edit the question.
                              Do not respond to questions that are not related to the creation of the loan note.
                              When you're finished, print out your input.
                              Use regular expressions to make your results easy to parse.
                              
                              Questions
                              1. 빌려주는 금액(loanAmount): "얼마를 빌려주시나요?"
                              2. 빌려주는 날짜(borrowedDate): "어느 날짜에 돈을 빌려주시나요? (YYYYMMDD 형식으로 입력해주세요)"
                              3. 이자율(InterestRate): "이자율은 몇 %로 하시겠습니까? (참고: 법정 최고 이자율은 20%입니다)"
                              4. 받을 날짜(Expiry date): "만기일은 언제로 할까요? (YYYYMMDD 형식으로 입력해주세요)"                            
""")

# 계약 작성 시작할 때 사용하는 서비스 메서드
# 대화 내용 저장
def get_initial_contract_response(user_id: int, service_id: int, chat: str) -> str:
    contracts_msg = HumanMessage(content=chat)

    response = contracts_model([contracts_sys, contracts_msg]).content
    conversation = {'user': chat, 'bot': response}
    
    save_history(user_id, service_id, conversation)

    return response

# 계약 작성 중 대화할 때 LLM 답변 요청하는 서비스 메서드
# 대화 내용 저장
def get_ongoing_contract_response(user_id: int, service_id: int, chat: str, history: dict) -> str:
    # conversation_history에서 이전 대화 내용 복원
    history_key = make_history_key(user_id, service_id)
    previous_conversations = conversation_history[history_key]
    conversations = ' '.join([item['user'] + item['bot'] for item in previous_conversations]) + chat
    contracts_msg = HumanMessage(content=conversations)

    response = contracts_model([contracts_sys, contracts_msg]).content
    conversation = {'user': chat, 'bot': response}

    save_history(user_id, service_id, conversation)

    return response

# 계약 작성 중 사용하는 서비스 메서드
def get_additional_request_response(user_id: int, service_id: int, chat: str) -> str:
    history_key = make_history_key(user_id, service_id)

    # conversation_history에 history_key로 저장된 내용이 있다면! (추후 히스토리 제한)
    conversations = conversation_history[history_key]
    response = get_ongoing_contract_response(user_id, service_id, chat, conversations)

    return response

# 계약보조 챗봇
## 나중에 user_id 토큰에서 가져오기
def get_assist_chat_response(user_id: int, contract_tmp_key: str, contract_info: ContractInfo, chat: str) -> str:
    chat_template = create_contract_info_template(contract_info)
    chat_template += create_chat_template(user_id, contract_tmp_key)

    # 메모리에 기존 대화가 저장되어 있으면 chat 더해주기
    if exist_conversations:
        chat_template += create_conversation_template(user_id, contract_tmp_key)
    
    contracts_msg = HumanMessage(content=chat_template)
    response = contracts_model([contracts_sys, contracts_msg]).content
    conversation = {'user': chat, 'bot': response}

    save_history(user_id, contract_tmp_key, conversation)

    return response


# Util

def create_contract_info_template(contract_info: ContractInfo):
    contract_info_template = f"""
        본인은 {contract_info.borrowedDate}에 {contract_info.loanAmount}원을 차용하였습니다.
        이자율은 연 {contract_info.interestRate}%이고 갚을 날짜는 {contract_info.maturityDate}입니다.

        특약사항
        {contract_info.specialAgreement or '없음'}
    """
    return contract_info_template

def create_chat_template(contract_info: ContractInfo, chat: str):
    chat_template = f"""
        위 정보는 지금까지 사용자가 입력한 계약 정보야.
        참고해서 '{chat}'에 대한 대답을 해줘
    """
    return chat_template

def create_conversation_template(user_id: int, contract_tmp_key: str):
    history_key = make_history_key(user_id, contract_tmp_key)
    conversations = conversation_history[history_key][-3:] # 최근 3개의 대화 기록만 가져옴
    
    conversation_template = "다음은 이전에 우리가 나누었던 대화야"

    for conversation in conversations:
        tmp_template = f""" 
            user: {conversation['user']}
            bot: {conversation['bot']}
        """
        conversation_template += tmp_template

    return conversation_template

def save_history(user_id: int, contract_tmp_key: int, conversation: dict):
    history_key = make_history_key(user_id, contract_tmp_key)
    conversation_history[history_key].append(conversation)

def make_history_key(user_id: int, contract_tmp_key: str) -> str:

    return str(user_id) + ':' + contract_tmp_key

def exist_conversations(user_id: int, contract_tmp_key: str) -> bool:
    history_key = make_history_key(user_id, contract_tmp_key)
    return history_key in conversation_history

def get_conversations(user_id: int, contract_tmp_key: str) -> str:
    return ContractInfo(contract_tmp_key)