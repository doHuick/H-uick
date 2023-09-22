from fastapi import FastAPI, UploadFile, HTTPException
from .services import contract_assist_service, contract_messenger_service
from ai.app.models.contract_info import ContractInfo

app = FastAPI()

@app.post("/contracts/assist")
async def create_assist_chat(user_id: int, contract_tmp_key: str, contract_info: ContractInfo, chat: str) -> str:
    try:
        response = contract_assist_service.create_assist_chat_response(user_id, contract_tmp_key, contract_info, chat)

        return {"answer": response}
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    
@app.post("/contracts/messenger")
async def create_messanger_contract(user_id: int, contract_tmp_key: str, file: UploadFile = UploadFile(...)):
    try:
        # 이미지 데이터 읽기
        image_data = await file.read()
        response = contract_messenger_service.create_messenger_contract(user_id, contract_tmp_key, image_data) # ContractInfo

        return response
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))