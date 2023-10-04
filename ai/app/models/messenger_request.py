from pydantic import BaseModel
from fastapi import UploadFile

class MessengerRequest(BaseModel):
    user_id: int
    contract_tmp_key: str