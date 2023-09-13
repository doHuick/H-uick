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