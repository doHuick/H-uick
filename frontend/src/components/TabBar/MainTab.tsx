import { useState } from 'react';
import styled from 'styled-components';

interface TabProps {
  active?: boolean;
  onClick?: () => void;
}

interface FeedTabProps {
  setNowActive: (keyword: string) => void;
}

// [23-09-10 : 메인페이지 차용 / 대여 탭 기능 구현]
export const MainTab = ({ setNowActive }: FeedTabProps) => {
  const [activeTab, setActiveTab] = useState('borrowing');

  const handleTabClick = (tab: string) => {
    setActiveTab(tab);
    setNowActive(tab);
  };
  return (
    <TabContainer>
      <Tab
        active={activeTab === 'borrowing'}
        onClick={() => handleTabClick('borrowing')}
      >
        빌린 돈
      </Tab>
      <Tab
        active={activeTab === 'rental'}
        onClick={() => handleTabClick('rental')}
      >
        빌려준 돈
      </Tab>
    </TabContainer>
  );
};

const TabContainer = styled.div`
  display: flex;
  height: 35px;
  align-items: center;
  line-height: 40px;
  margin: 14px 14px 11px 14px;
`;

const Tab = styled.div<TabProps>`
  width: 100%;
  height: 35px;
  font-size: 20px;
  font-weight: 500;
  text-align: center;
  text-decoration: none;
  color: ${(props: TabProps) =>
    props.active ? 'var(--huick-blue)' : 'var(--gray)'};
  border-bottom: ${(props: TabProps) =>
    props.active ? '2px solid var(--huick-blue)' : 'none'};
  cursor: pointer;
  &:hover {
    color: var(--huick-blue);
  }
`;
