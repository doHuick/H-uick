from pydantic import BaseModel
from typing import Optional

class ContractInfo(BaseModel):
    loanAmount: int  # 빌린 금액
    borrowedDate: str  # 빌린 날짜
    interestRate: float  # 이자율
    maturityDate: Optional[str]  # 갚을 날짜
    specialAgreement: Optional[str]  # 특약사항(선택)