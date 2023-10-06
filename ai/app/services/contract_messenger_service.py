from ..models.contract_info import ContractInfo
from decouple import config
from langchain.chat_models import ChatOpenAI
from langchain.schema import HumanMessage, SystemMessage
from collections import defaultdict
import easyocr
import json
from datetime import datetime

conversation_history = defaultdict(list)

reader = easyocr.Reader(['ko'])

now = datetime.now()

# OpenAI API model 설정
MODEL_NAME = 'gpt-4'
TEMPERATURE = 0.5
OPENAI_API_KEY = config('OPENAI_API_KEY')

messenger_model = ChatOpenAI(model_name=MODEL_NAME, temperature=TEMPERATURE, openai_api_key=OPENAI_API_KEY)

# System 프롬프트 작성
messenger_sys = SystemMessage(content=
                                """
                                When you respond, you'll use the following structure: 
                                {"contract_info": {"loanAmount": 1000, "interestRate": 3.2, "maturityDate": "20210201"}}.
                                Do not include anything else.

                                It's an OCR extraction of what was said in the messenger.
                                So there are some inaccuracies. You'll have to infer them.
                                The goal is to extract the information needed for a loan agreement between two people promising to lend money.
                                Today's date is """ + str(now.date()) + """.
                                Currently, the maximum legal interest rate is 20% (except for small amounts up to 100,000 won).

                                The dialogue will include the amount to be lent (loanAmount),
                                the interest rate (interestRate), and the date to be received (maturityDate).
                                """
                              )

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