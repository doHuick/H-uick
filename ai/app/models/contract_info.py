from pydantic import BaseModel
from typing import Optional

class ContractInfo(BaseModel):
    loanAmount: Optional[int] = None  # 빌려주는 금액
    interestRate: Optional[float] = None  # 이자율
    maturityDate: Optional[str] = None  # 만기 날짜