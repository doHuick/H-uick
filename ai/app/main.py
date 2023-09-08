from fastapi import FastAPI, HTTPException, UploadFile, Form, File, Depends
from .services import llm_service


app = FastAPI()

def get_pdf(pdf: UploadFile = File(None)) -> UploadFile:
    return pdf

@app.post("/contracts/start/")
async def chat_openai(user_id: int, service_id: int, chat: str, pdf: UploadFile = Depends(get_pdf)):
    try:
        if pdf:
            response = llm_service.get_initial_contract_response_with_pdf(user_id, service_id, pdf, chat)
        else:
            response = llm_service.get_initial_contract_response(user_id, service_id, chat)

        return {"answer": response}
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    
@app.post("/contracts/continue/")
async def additional_request_openai(user_id: int, service_id: int, chat: str):
    try:
        response = llm_service.get_additional_request_response(user_id, service_id, chat)
        return {"answer": response}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))