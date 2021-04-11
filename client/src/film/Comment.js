import React, {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {yupResolver} from '@hookform/resolvers/yup';
import * as yup from 'yup';
import {API_BASE_URL, MAX_CHOICES} from "../constants";
import {Form, Input, Button, Icon, Select, Col, notification} from 'antd';
import {createComment, createFilm} from "../util/APIUtils";
import {useHistory} from "react-router-dom";

import SockJsClient from "react-stomp";


const FormItem = Form.Item;



const schema = yup.object().shape({
    comment: yup.string().required(),
});

const Comment = ({filmSlug}) => {
    let history = useHistory();
    const [isLoading, setIsLoading] = useState(true);
    const [connected, setConnected] = useState(false);


    const submitComment = (data,e) => {
        createComment(data,filmSlug).then(response => {
            notification.success({
                message: 'Film App',
                description: response.message
            });
            e.target.reset();
        }).catch(error => {
        });

        /*((data,filmSlug) => {
            debugger
            console.log("in cornnect ");
            stompClient.connect({}, function (frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                //stompClient.send(`/film/${filmSlug}/comment/create`, {}, JSON.stringify(data));
                createComment(data,filmSlug)
                    .then(response => {
                        notification.success({
                            message: 'Film App',
                            description: response.message
                        });
                        e.target.reset();
                        }).catch(error => {
                    });
                stompClient.subscribe('/topic/films', function (films) {
                    receiveMessages(films);
                });
            });
        })(data,filmSlug);
*/

       /* createComment(data,filmSlug)
            .then(response => {
                notification.success({
                    message: 'Film App',
                    description: response.message
                });
                e.target.reset();
            }).catch(error => {
            if (error.status === 401) {
                this.props.handleLogout('/login', 'error', 'You have been logged out. Please login to create film.');
            } else {
                notification.error({
                    message: 'Film App',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });
            }
        });*/
    }

    const {
        register, handleSubmit, values,
        touched,
        errors,
        isSubmitting,
        handleChange,
        handleBlur,
    } = useForm({
        resolver: yupResolver(schema),
    });


    return (
        <Form onSubmit={handleSubmit((data,e) => submitComment(data,e))}  className="create-film-form">
            <FormItem validateStatus={errors.comment &&  "error"}
                      help={(errors.comment) ? errors.comment.message : ''} className="film-form-row">
                        <textarea ref={register}
                                  placeholder="Enter your comment"
                                  style={{fontSize: '16px'}}
                                  autosize={{minRows: 3, maxRows: 6}}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  className = "textarea"
                                  name="comment"/>

            </FormItem>

            <FormItem className="film-form-row">
                <Button type="primary"
                        htmlType="submit"
                        size="large"
                        className="create-film-form-button">Post Comment</Button>
            </FormItem>
            <SockJsClient url={`${API_BASE_URL}/ws`}
                          topics={['/user/queue/notification']}
                          ref={ (client) => { this.clientRef = client }}
                          onMessage={(response) => {
                              debugger
                              notification.success({
                                  message: 'Film App',
                                  description: response.message
                              });
                          }}
                          onConnect={()=>{

                          }}
            />
        </Form>
    );
};

export default Comment;