from fastapi import FastAPI, HTTPException
from .services import llm_service
from ai.app.models.contract_info import ContractInfo


app = FastAPI()

@app.post("/contracts/start/")
async def request_initial_contracts(user_id: int, service_id: int, chat: str):
    try:
        response = llm_service.get_initial_contract_response(user_id, service_id, chat)

        return {"answer": response}
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    
@app.post("/contracts/continue/")
async def request_additional_contracts(user_id: int, service_id: int, chat: str):
    try:
        response = llm_service.get_additional_request_response(user_id, service_id, chat)

        return {"answer": response}
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# 채팅내용을 토대로 계약 내용을 추출하는 기능
@app.post("/contracts/chat/")
async def request_additional_contracts(lender_id: int, borrower_id: int, service_id: int, chat: str):
    try:
        response = llm_service.get_additional_request_response(user_id, service_id, chat)

        return {"answer": response}
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    

@app.post("/chats/assistant/")
async def request_assist_chats(user_id: int, contract_tmp_key: str, contract_info: ContractInfo, chat: str):
    try:
        response = llm_service.get_assist_chat_response(user_id, contract_tmp_key, contract_info, chat)

        return {"answer": response}
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))