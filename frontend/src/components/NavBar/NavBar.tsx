import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import { ReactComponent as MainSvg } from '../../assets/icons/mainpage.svg';
import { ReactComponent as MySvg } from '../../assets/icons/mypage.svg';
import { ReactComponent as ChatbotSvg } from '../../assets/icons/contract.svg';

type NavItemProps = {
  active: boolean;
  onClick: () => void;
};

const NavBar = () => {
  const [activeNav, setActiveNav] = useState<string | null>(null);
  const location = useLocation();
  const navigate = useNavigate();

  const getActiveNav = (): string | null => {
    const active = Object.keys(navRoutes).find((nav) => isNavActive(nav));
    return active || null;
  };

  useEffect(() => {
    setActiveNav(getActiveNav());
  }, []);

  const handleNavClick = (nav: string) => {
    setActiveNav(nav);
    navigate(navRoutes[nav]);
  };

  const navRoutes: Record<string, string> = {
    main: '/',
    chatbot: '/chatbot',
    mypage: '/mypage',
  };

  const isNavActive = (nav: string) => location.pathname === navRoutes[nav];

  return (
    <BottomBar>
      <NavItem
        onClick={() => handleNavClick('main')}
        active={activeNav === 'main'}
      >
        <MainSvg />
        홈
      </NavItem>
      <NavItem
        onClick={() => handleNavClick('chatbot')}
        active={activeNav === 'chatbot'}
      >
        <ChatbotSvg />
        계약
      </NavItem>
      <NavItem
        onClick={() => handleNavClick('mypage')}
        active={activeNav === 'mypage'}
      >
        <MySvg />
        내 정보
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
  height: 70px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f0f0;
`;

const NavItem = styled.div<NavItemProps>`
  width: 33.3%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  flex-direction: column;
  position: relative;
  svg {
    cursor: pointer;
    g rect {
      fill: ${(props) => (props.active ? 'var(--huick-blue)' : 'var(--gray)')};
    }
  }
  color: ${(props) => (props.active ? 'var(--huick-blue)' : 'var(--gray)')};
`;
