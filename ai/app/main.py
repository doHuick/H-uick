from fastapi import FastAPI, HTTPException, UploadFile, Form, File, Depends
from .services import llm_service
from app.services.models.contract_info import ContractInfo


app = FastAPI()

def get_pdf(pdf: UploadFile = File(None)) -> UploadFile:
    return pdf

@app.post("/contracts/start/")
async def request_initial_contracts(user_id: int, service_id: int, chat: str, file: UploadFile = Depends(get_pdf)):
    try:
        if file:
            file_contents = await file.read()
            response = llm_service.get_initial_contract_response_with_pdf(user_id, service_id, file_contents, chat)
        else:
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


@app.post("/contracts/convert-to-pdf/")
async def request_convert_to_pdf(contract_info: ContractInfo):
    try:
        response = llm_service.convert_to_pdf(contract_info)
        return {"answer": response}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))