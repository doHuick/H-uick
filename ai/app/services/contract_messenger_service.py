from ..models.contract_info import ContractInfo
from decouple import config
from langchain.chat_models import ChatOpenAI
from langchain.schema import HumanMessage, SystemMessage
from collections import defaultdict
import easyocr
import json

conversation_history = defaultdict(list)

reader = easyocr.Reader(['ko'])

# OpenAI API model 설정
MODEL_NAME = 'gpt-4'
TEMPERATURE = 0
OPENAI_API_KEY = config('OPENAI_API_KEY')

messenger_model = ChatOpenAI(model_name=MODEL_NAME, temperature=TEMPERATURE, openai_api_key=OPENAI_API_KEY)
# System 프롬프트 작성
messenger_sys = SystemMessage(content="""
                              메신저에서 대화한 내용을 OCR로 추출한거야.
                              그래서 부정확한 부분이 있어. 그런 부분들은 유추해줘.
                              두 사람이 돈을 빌려주겠다는 약속을 하는 내용인데, 차용 계약서에 필요한 정보를 추출하는게 목표야.

                              대화내용에는 빌려주는 금액(loanAmount), 이자율(interestRate), 받을 날짜(maturityDate)가 포함될거야.
                              답변해줄 때는 {"contract_info": {"loanAmount: 1000, interestRate: 3, maturityDate: 20210201"}}과 같은 형태로 답변해줘
                              이외의 내용은 포함하지 말아줘.
                              """)

def create_messenger_contract(user_id: int, contract_tmp_key: str, image_data: bytes):
    # 1. 이미지에서 텍스트 추출
    extracted_text = extract_text_from_image(image_data)

    # 2. GPT에게 계약 정보 추출해달라고 요청
    messenger_msg = HumanMessage(content=extracted_text)
    response_content = messenger_model([messenger_sys, messenger_msg]).content

    # 3. 계약 정보를 JSON으로 추출
    response_json = json.loads(response_content)
    contract_info_data = response_json.get("contract_info", {})

    # 4. ContractInfo 객체로 생성
    contract_info_obj = ContractInfo(**contract_info_data)

    return contract_info_obj

################ UTIL ################

# Image OCR
def extract_text_from_image(image_data: bytes) -> str:
    result = reader.readtext(image_data)
    extracted_text = "\n".join([item[1] for item in result])
    return extracted_text