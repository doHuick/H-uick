from ..models.contract_info import ContractInfo
from decouple import config
from langchain.chat_models import ChatOpenAI
from langchain.schema import HumanMessage, SystemMessage
from collections import defaultdict

conversation_history = defaultdict(list)

# OpenAI API model 설정
MODEL_NAME = 'gpt-3.5-turbo'
TEMPERATURE = 0.8
OPENAI_API_KEY = config('OPENAI_API_KEY')

assist_model = ChatOpenAI(model_name=MODEL_NAME, temperature=TEMPERATURE, openai_api_key=OPENAI_API_KEY)

# System 프롬프트 작성
assist_sys = SystemMessage(content="""
                              Communicate with users in Korean.
                              You are a chatbot that assists with loan contract creation.
                              The user must answer in a set format to complete the contract.
                              But I'll call you when a user has a question or doesn't answer in the prescribed format.
                              Do not respond to questions that are not related to the creation of the loan contract.
                              The list of questions looks like this.
                              1. 빌려주는 금액(loanAmount): "얼마를 빌려주시나요? ('1000원'과 같은 형태로 입력해주세요)"
                              2. 이자율(interestRate): "이자율은 몇 %로 하시겠습니까? ('3%'와 같은 형태로 입력해주세요)"
                              3. 받을 날짜(maturityDate): "만기일은 언제로 할까요? ('YYYYMMDD' 형식으로 입력해주세요)"                          
                              """)

# 계약보조 챗봇
# 나중에 user_id 토큰에서 가져오기
def create_assist_chat_response(user_id: int, tmp_contract_key: str, contract_info: ContractInfo, chat: str):
    chat_template = create_contract_info_template(contract_info)
    chat_template += create_chat_template(contract_info, chat)

    # 메모리에 기존 대화가 저장되어 있으면 chat 더해주기
    if exist_conversation:
        chat_template += create_conversation_template(user_id, tmp_contract_key)
    
    assist_msg = HumanMessage(content=chat_template)
    response = assist_model([assist_sys, assist_msg]).content
    conversation = {'user': chat, 'bot': response}

    save_history(user_id, tmp_contract_key, conversation)

    return response


################ UTIL ################

# ContractInfo를 Human 프롬프트로 만들기
def create_contract_info_template(contract_info: ContractInfo):
    contract_info_template = f"""
    1. "얼마를 빌려주시나요? ('1000원'과 같은 형태로 입력해주세요)" {contract_info.loanAmount}원
    2. "이자율은 몇 %로 하시겠습니까? ('3%'와 같은 형태로 입력해주세요)" 연 {contract_info.interestRate}%
    3. 받을 날짜(maturityDate): "만기일은 언제로 할까요? ('YYYYMMDD' 형식으로 입력해주세요)" {contract_info.maturityDate}
    
    Anything that is None is either a question that hasn't been asked yet or hasn't been answered by the user.
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
def create_conversation_template(user_id: int, tmp_contract_key: str):
    conversations = get_conversation(user_id, tmp_contract_key, 3) # 최근 대화 3개로 제한
    
    conversation_template = "다음은 이전에 우리가 나누었던 대화야"

    for conversation in conversations:
        tmp_template = f""" 
            user: {conversation['user']}
            bot: {conversation['bot']}
        """
        conversation_template += tmp_template

    return conversation_template

# 대화내용 저장 키 만들기
def make_history_key(user_id: int, tmp_contract_key: str) -> str:

    return str(user_id) + ':' + tmp_contract_key

# (Update 예정) 대화를 메모리에 저장하기
def save_history(user_id: int, tmp_contract_key: int, conversation: dict):
    history_key = make_history_key(user_id, tmp_contract_key)
    conversation_history[history_key].append(conversation)

# (Update 예정) 저장된 대화내용이 있는지 확인하기
def exist_conversation(user_id: int, tmp_contract_key: str) -> bool:
    history_key = make_history_key(user_id, tmp_contract_key)

    return history_key in conversation_history

# (Update 예정) 저장된 대화내용 가져오기
def get_conversation(user_id: int, tmp_contract_key: str, limit: int) -> str:
    history_key = make_history_key(user_id, tmp_contract_key)
    conversations = conversation_history[history_key][-limit:] # 최근 {limit}개의 대화 기록만 가져옴

    return conversations