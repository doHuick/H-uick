import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import { ReactComponent as MainSvg } from '../../assets/icons/mainpage.svg';
import { ReactComponent as MySvg } from '../../assets/icons/mypage.svg';
import { ReactComponent as ContractSvg } from '../../assets/icons/contract.svg';
import { ReactComponent as ChatbotSvg } from '../../assets/icons/chatbot.svg';

type NavItemProps = {
  active: boolean;
  onClick: () => void;
};

const NavBar = () => {
  const [activeNav, setActiveNav] = useState<string | null>(null);
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    setActiveNav(getActiveNav());
  }, []);

  const handleNavClick = (nav: string) => {
    setActiveNav(nav);
    switch (nav) {
      case 'main':
        navigate('/main');
        break;
      case 'contract':
        navigate('/contract');
        break;
      case 'chatbot':
        navigate('/chatbot');
        break;
      case 'mypage':
        navigate('/mypage');
        break;
    }
  };

  const navRoutes: Record<string, string> = {
    main: '/main',
    contract: '/contract',
    chatbot: '/chatbot',
    mypage: '/mypage',
  };

  const isNavActive = (nav: string) => location.pathname === navRoutes[nav];

  const getActiveNav = (): string | null => {
    for (const nav in navRoutes) {
      if (isNavActive(nav)) {
        return nav;
      }
    }
    return null;
  };

  return (
    <BottomBar>
      <NavItem
        onClick={() => handleNavClick('main')}
        active={activeNav === 'main'}
      >
        <MainSvg />
      </NavItem>
      <NavItem
        onClick={() => handleNavClick('contract')}
        active={activeNav === 'contract'}
      >
        <ContractSvg />
      </NavItem>
      <NavItem
        onClick={() => handleNavClick('chatbot')}
        active={activeNav === 'chatbot'}
      >
        <ChatbotSvg />
      </NavItem>
      <NavItem
        onClick={() => handleNavClick('mypage')}
        active={activeNav === 'mypage'}
      >
        <MySvg />
      </NavItem>
    </BottomBar>
  );
};

export default NavBar;

const BottomBar = styled.div`
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 70px;
  background-color: var(--white);
`;

const NavItem = styled.div<NavItemProps>`
  svg {
    cursor: pointer;
    g rect {
      fill: ${(props) => (props.active ? 'var(--huick-blue)' : 'var(--gray)')};
    }
  }
`;
