from decouple import config
from langchain.chat_models import ChatOpenAI
from langchain.schema import AIMessage, HumanMessage, SystemMessage
from langchain.document_loaders import PyPDFLoader
from fastapi import UploadFile
import io

from collections import defaultdict

chat_history = defaultdict(list)

chat_model = ChatOpenAI(model_name='gpt-3.5-turbo', openai_api_key=config('OPENAI_API_KEY'))


# 계약 작성 시작할 때 사용하는 서비스 메서드
# 대화 내용 저장
def get_initial_contract_response(user_id: int, service_id: int, chat: str) -> str:
    response = chat_model.predict(chat)
    conversation = {'user': chat, 'bot': response}
    save_history(user_id, service_id, conversation)
    return response


# 계약 작성 중 대화할 때 LLM 답변 요청하는 서비스 메서드
# 대화 내용 저장
def get_ongoing_contract_response(user_id: int, service_id: int, chat: str, history: dict) -> str:
    conversations = ' '.join([item['user'] + item['bot'] for item in history]) + chat
    response = chat_model.predict(conversations)
    conversation = {'user': chat, 'bot': response}
    save_history(user_id, service_id, conversation)
    return response


# 계약 작성 시작할 때 사용하는 서비스 메서드(PDF 업로드 기능)
# 대화 내용 저장(get_contract_response에 포함)
def get_initial_contract_response_with_pdf(user_id: int, service_id: int, pdf: UploadFile, chat: str) -> str:
    pdf_text = PyPDFLoader(pdf)
    request_text = pdf_text + chat

    return get_initial_contract_response(user_id, service_id, request_text)


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