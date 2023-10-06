from pydantic import BaseModel
from .contract_info import ContractInfo

class AssistRequest(BaseModel):
    user_id: int
    contract_tmp_key: str
    contract_info: ContractInfo
    chat: str