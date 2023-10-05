from fastapi import FastAPI, HTTPException, UploadFile
from .services import contract_assist_service, contract_messenger_service
from .models.assist_request import AssistRequest
from fastapi.middleware.cors import CORSMiddleware


app = FastAPI()

@app.post("/contracts/assist")
async def create_assist_chat(assist_request: AssistRequest):
    try:
        response = contract_assist_service.create_assist_chat_response(assist_request.user_id, 
                                                                       assist_request.contract_tmp_key,
                                                                       assist_request.contract_info,
                                                                       assist_request.chat)

        return {"answer": response}
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    
@app.post("/contracts/messenger")
async def create_messanger_contract(user_id: int, contract_tmp_key: str, file: UploadFile = UploadFile(...)):
    try:
        # 이미지 데이터 읽기
        image_data = await file.read()
        response = contract_messenger_service.create_messenger_contract(user_id,
                                                                        contract_tmp_key,
                                                                        image_data)

        return response
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))