from fastapi import FastAPI, HTTPException, UploadFile
from .services import contract_assist_service, contract_messenger_service
from .models.assist_request import AssistRequest
from fastapi.middleware.cors import CORSMiddleware



app = FastAPI()

# CORS 미들웨어 설정
origins = [
    # 허용하려는 출처
    "https://localhost:5173",
    "http://localhost:5173",
    "http://www.h-uick.com",
    "https://www.h-uick.com",
    "http://h-uick.com",
    "https://h-uick.com"
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

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