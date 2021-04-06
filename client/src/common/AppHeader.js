import React, {Component} from 'react';
import {
    Link,
    withRouter
} from 'react-router-dom';
import './AppHeader.css';

import {Layout, Menu, Dropdown, Icon} from 'antd';

const Header = Layout.Header;

const AppHeader = (props) => {
    function handleMenuClick({key}) {
        if (key === "logout") {
            props.onLogout();
        }
    }

    let menuItems;
    if (props.currentUser) {
        menuItems = [
            <Menu.Item key="/">
                <Link to="/">
                    <i className="fas fa-home"></i>
                </Link>
            </Menu.Item>,
            <Menu.Item key="/film/new">
                <Link to="/film/new">
                    <i className="fas fa-film"></i>
                </Link>
            </Menu.Item>,
            <Menu.Item key="/profile" className="profile-menu">
                <ProfileDropdownMenu
                    currentUser={props.currentUser}
                    handleMenuClick={handleMenuClick}/>
            </Menu.Item>
        ];
    } else {
        menuItems = [
            <Menu.Item key="/login">
                <Link to="/login">Login</Link>
            </Menu.Item>,
            <Menu.Item key="/signup">
                <Link to="/signup">Signup</Link>
            </Menu.Item>
        ];
    }

    return (
        <Header className="app-header">
            <div className="container">
                <div className="app-title">
                    <Link to="/">Film App</Link>
                </div>
                <Menu
                    className="app-menu"
                    mode="horizontal"
                    selectedKeys={[props.location.pathname]}
                    style={{lineHeight: '64px'}}>
                    {menuItems}
                </Menu>
            </div>
        </Header>
    );

}

function ProfileDropdownMenu(props) {
    const dropdownMenu = (
        <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
            <Menu.Item key="user-info" className="dropdown-item" disabled>
                <div className="user-full-name-info">
                    {props.currentUser.name}
                </div>
                <div className="username-info">
                    @{props.currentUser.username}
                </div>
            </Menu.Item>
            <Menu.Divider/>
            <Menu.Item key="logout" className="dropdown-item">
                Logout
            </Menu.Item>
        </Menu>
    );

    return (
        <Dropdown
            overlay={dropdownMenu}
            trigger={['click']}
            getPopupContainer={() => document.getElementsByClassName('profile-menu')[0]}>
            <a className="ant-dropdown-link">
                <i className="fas fa-user"></i> <Icon type="down"/>
            </a>
        </Dropdown>
    );
}


export default withRouter(AppHeader);