from decouple import config

from langchain.chat_models import ChatOpenAI
from langchain.schema import HumanMessage, SystemMessage

from collections import defaultdict

from ai.app.models.contract_info import ContractInfo

conversation_history = defaultdict(list)

# OpenAI API model 설정
MODEL_NAME = 'gpt-4'
TEMPERATURE = 0.8
OPENAI_API_KEY = config('OPENAI_API_KEY')

contracts_model = ChatOpenAI(model_name=MODEL_NAME, temperature=TEMPERATURE, openai_api_key=OPENAI_API_KEY)
# System 프롬프트 작성
contracts_sys = SystemMessage(content="""
    """)

# 계약보조 챗봇
# 나중에 user_id 토큰에서 가져오기
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

# ContractInfo를 Human 프롬프트로 만들기
def create_contract_info_template(contract_info: ContractInfo):
    contract_info_template = f"""
        본인은 {contract_info.borrowedDate}에 {contract_info.loanAmount}원을 차용하였습니다.
        이자율은 연 {contract_info.interestRate}%이고 갚을 날짜는 {contract_info.maturityDate}입니다.

        특약사항
        {contract_info.specialAgreement or '없음'}
    """
    return contract_info_template

# 유저 chat을 Human 프롬프트로 만들기
def create_chat_template(contract_info: ContractInfo, chat: str):
    chat_template = f"""
        위 정보는 지금까지 사용자가 입력한 계약 정보야.
        참고해서 '{chat}'에 대한 대답을 해줘
    """
    return chat_template

# 이전 대화 내용을 Human 프롬프트로 만들기
def create_conversation_template(user_id: int, contract_tmp_key: str):
    conversations = get_conversations(user_id, contract_tmp_key, 3) # 최근 대화 3개로 제한
    
    conversation_template = "다음은 이전에 우리가 나누었던 대화야"

    for conversation in conversations:
        tmp_template = f""" 
            user: {conversation['user']}
            bot: {conversation['bot']}
        """
        conversation_template += tmp_template

    return conversation_template

# 대화내용 저장 키 만들기
def make_history_key(user_id: int, contract_tmp_key: str) -> str:

    return str(user_id) + ':' + contract_tmp_key

# (Update 예정) 대화를 메모리에 저장하기
def save_history(user_id: int, contract_tmp_key: int, conversation: dict):
    history_key = make_history_key(user_id, contract_tmp_key)
    conversation_history[history_key].append(conversation)

# (Update 예정) 저장된 대화내용이 있는지 확인하기
def exist_conversations(user_id: int, contract_tmp_key: str) -> bool:
    history_key = make_history_key(user_id, contract_tmp_key)
    return history_key in conversation_history

# (Update 예정) 저장된 대화내용 가져오기
def get_conversations(user_id: int, contract_tmp_key: str, limit: int) -> str:
    history_key = make_history_key(user_id, contract_tmp_key)
    conversations = conversation_history[history_key][-limit:] # 최근 {limit}개의 대화 기록만 가져옴
    return conversations