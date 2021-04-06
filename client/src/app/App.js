import React, {Component, useState, useEffect} from 'react';
import './App.css';
import {
    Route,
    withRouter,
    Switch, useHistory
} from 'react-router-dom';

import {getCurrentUser} from '../util/APIUtils';
import {ACCESS_TOKEN} from '../constants';

import FilmList from '../film/FilmList';
import NewFilm from '../film/NewFilm';
import Login from '../user/login/Login';
import Signup from '../user/signup/Signup';
import SingleFilm from '../film/SingleFilm';
import AppHeader from '../common/AppHeader';
import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';
import PrivateRoute from '../common/PrivateRoute';

import {Layout, notification} from 'antd';

const {Content} = Layout;

const App = () => {
    const [currentUser, setCurrentUser] = useState(null);
    const [isAuthenticated, setIsAuthenticated] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    let history = useHistory();
    useEffect(() => {
        loadCurrentUser();
    },[]);
    notification.config({
        placement: 'topRight',
        top: 70,
        duration: 3,
    });


    function loadCurrentUser() {
        setIsLoading(true);
        getCurrentUser()
            .then(response => {
                setCurrentUser(response)
                setIsAuthenticated(true)
                setIsLoading(false)
            }).catch(error => {
            setIsLoading(false)
        });
    }


    function handleLogout(redirectTo = "/", notificationType = "success", description = "You're successfully logged out.") {
        localStorage.removeItem(ACCESS_TOKEN);
        setIsAuthenticated(false);
        setCurrentUser(null);

        history.push(redirectTo);

        notification[notificationType]({
            message: 'Film App',
            description: description,
        });
    }

    function handleLogin() {
        notification.success({
            message: 'Film App',
            description: "You're successfully logged in.",
        });
        loadCurrentUser();
        history.push("/");
    }



    return (
       (isLoading) ? <LoadingIndicator/> : (<Layout className="app-container">
           <AppHeader isAuthenticated={isAuthenticated}
                      currentUser={currentUser}
                      onLogout={handleLogout}/>

           <Content className="app-content">
               <div className="container">
                   <Switch>
                       <Route exact path="/"
                              render={(props) => <FilmList isAuthenticated={isAuthenticated}
                                                           currentUser={currentUser}
                                                           handleLogout={handleLogout} {...props} />}>
                       </Route>
                       <Route path="/login"
                              render={(props) => <Login onLogin={handleLogin} {...props} />}></Route>
                       <Route path="/signup" component={Signup}></Route>
                       <Route path="/films/:filmslug"
                              render={(props) => <SingleFilm isAuthenticated={isAuthenticated}
                                                             currentUser={currentUser} {...props}  />}>
                       </Route>
                       <PrivateRoute authenticated={isAuthenticated} path="/film/new"
                                     component={NewFilm} handleLogout={handleLogout}></PrivateRoute>
                       <Route component={NotFound}></Route>
                   </Switch>
               </div>
           </Content>
       </Layout>)
    );

}

export default withRouter(App);
