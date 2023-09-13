from decouple import config

from langchain.chat_models import ChatOpenAI
from langchain.schema import HumanMessage, SystemMessage

from collections import defaultdict

from ai.app.models.contract_info import ContractInfo

chat_history = defaultdict(list)

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
    # chat_history에서 이전 대화 내용 복원
    history_key = make_history_key(user_id, service_id)
    previous_conversations = chat_history[history_key]
    conversations = ' '.join([item['user'] + item['bot'] for item in previous_conversations]) + chat
    contracts_msg = HumanMessage(content=conversations)

    response = contracts_model([contracts_sys, contracts_msg]).content
    conversation = {'user': chat, 'bot': response}

    save_history(user_id, service_id, conversation)

    return response

# 계약 작성 중 사용하는 서비스 메서드
def get_additional_request_response(user_id: int, service_id: int, chat: str) -> str:
    history_key = make_history_key(user_id, service_id)

    # chat_history에 history_key로 저장된 내용이 있다면! (추후 히스토리 제한)
    conversations = chat_history[history_key]
    response = get_ongoing_contract_response(user_id, service_id, chat, conversations)

    return response

# Util

def save_history(user_id: int, service_id: int, conversation: dict):
    history_key = make_history_key(user_id, service_id)
    chat_history[history_key].append(conversation)

def make_history_key(user_id: int, service_id: int) -> str:

    return str(user_id) + ':' + str(service_id)