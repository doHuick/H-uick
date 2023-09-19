from fastapi import FastAPI, HTTPException
from .services import llm_service
from ai.app.models.contract_info import ContractInfo

app = FastAPI()

@app.post("/chats/assistant/")
async def request_assist_chats(user_id: int, contract_tmp_key: str, contract_info: ContractInfo, chat: str):
    try:
        response = llm_service.get_assist_chat_response(user_id, contract_tmp_key, contract_info, chat)

        return {"answer": response}
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))