from pydantic import BaseModel
from typing import Optional

class ContractInfo(BaseModel):
    loanAmount: Optional[int] = None  # 빌린 금액
    borrowedDate: Optional[str] = None  # 빌린 날짜
    interestRate: Optional[float] = None  # 이자율
    maturityDate: Optional[str] = None  # 갚을 날짜
    specialAgreement: Optional[str] = None  # 특약사항(선택)