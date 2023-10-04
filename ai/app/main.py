from fastapi import FastAPI, HTTPException
from .services import contract_assist_service, contract_messenger_service
from .models.assist_request import AssistRequest
from .models.messenger_request import MessengerRequest

app = FastAPI()

@app.post("/contracts/assist")
async def create_assist_chat(assist_request: AssistRequest) -> str:
    try:
        response = contract_assist_service.create_assist_chat_response(assist_request.user_id, 
                                                                       assist_request.contract_tmp_key,
                                                                       assist_request.contract_info,
                                                                       assist_request.chat)

        return {"answer": response}
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    
@app.post("/contracts/messenger")
async def create_messanger_contract(messenger_reqeust: MessengerRequest):
    try:
        # 이미지 데이터 읽기
        image_data = await messenger_reqeust.file.read()
        response = contract_messenger_service.create_messenger_contract(messenger_reqeust.user_id,
                                                                        messenger_reqeust.contract_tmp_key,
                                                                        image_data)

        return response
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))