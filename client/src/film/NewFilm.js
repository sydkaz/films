import React, {Component, useEffect, useState} from 'react';
import {createFilm} from '../util/APIUtils';
import {useHistory} from "react-router-dom";

import './NewFilm.css';
import {Form, Input, Button, Icon, Select, Col, notification, DatePicker} from 'antd';
import * as yup from "yup";
import {useForm} from "react-hook-form";
import {yupResolver} from "@hookform/resolvers/yup";
import IssuePhotoUpload from "./IssuePhotoUpload";
import {convertToSlug} from "../util/Helpers";
import SockJS from 'sockjs-client'; // Note this line

import {ACCESS_TOKEN, API_BASE_URL} from "../constants";
import SockJsClient from "react-stomp";
const Option = Select.Option;
const FormItem = Form.Item;
const {TextArea} = Input
let stompClient = null;
const schema = yup.object().shape({
    name: yup.string().required(),
    description: yup.string().required(),
    releaseDate: yup.string().required(),
    rating: yup.string().required(),
    ticketPrice: yup.string().required(),
    country: yup.string().required(),
    genere: yup.string().required(),
    photo: yup.string()
});

const NewFilm = () => {

    let history = useHistory();
    const submitForm = (data) => {
        const filmObject = {
            country: data.country,
            description: data.description,
            genre: data.genere,
            name: data.name,
            photo: data.photo,
            rating: data.rating,
            releaseDate: data.releaseDate,
            slug: convertToSlug(data.name),
            ticketPrice: data.ticketPrice
        };
        this.clientRef.sendMessage(`/film/new/create`, JSON.stringify(filmObject));
    }

    const callbackArray = [() => console.log('Hi!'), () => console.log('Ho!')];

    const callback = () => console.log('Hello!');

    const handleChangeStatus = ({meta, file, xhr}) => {
        if (xhr.status === 200) {
            setValue("photo", JSON.parse(xhr.responseText)[0].location);
        }
    };

    const removedfile = file => console.log('removing...', file);

    const dropzone = null;


    const handleFileAdded = (file) => {

    }

    const handlePost = () => {
        console.log('Final Array' + this.dropzone.getQueuedFiles());
    }

    function onChange(date, dateString) {
        setValue("releaseDate", dateString);
    }

    const {
        register, handleSubmit, values,
        touched,
        errors,
        isSubmitting,
        handleChange,
        handleBlur,
        setValue
    } = useForm({
        resolver: yupResolver(schema),
        validationSchema: schema
    });

    return (
        <div className="new-film-container">
            <SockJsClient url={`${API_BASE_URL}/ws`}
                          topics={['/user/queue/notification']}
                          ref={ (client) => { this.clientRef = client }}
                          onMessage={(response) => {
                              notification.success({
                                  message: 'Film App',
                                  description: response.message
                              });
                          }}
                          onConnect={()=>{

                          }}
            />

            <h1 className="page-title">Create New Film</h1>
            <div className="new-film-content">
                <Form onSubmit={handleSubmit((d) => submitForm(d))} className="create-film-form">
                    <FormItem validateStatus={(errors.name) && "error"}
                              help={(errors.name) ? errors.name.message : ''} className="film-form-row">
                        <input
                            placeholder="Enter Movie Name"
                            style={{fontSize: '16px', width: '100%'}}
                            name="name"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            ref={register}

                        />
                    </FormItem>
                    <FormItem validateStatus={(errors.releaseDate) && "error"}
                              help={(errors.releaseDate) ? errors.releaseDate.message : ''} className="film-form-row">
                        {/* <input
                            placeholder="Enter Release Date"
                            style={{fontSize: '16px', width: '100%'}}
                            name="releaseDate"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            ref={register}
                        />*/}
                        <input
                            name="releaseDate"
                            type='hidden'
                            onChange={handleChange}
                            onBlur={handleBlur}
                            ref={register}
                        />
                        <div className={"datepicker"}>
                            <DatePicker onChange={onChange} picker="year" placeholder="Enter Release Date"
                                        style={{fontSize: '16px', width: '100%'}}
                            />
                        </div>
                    </FormItem>
                    <FormItem validateStatus={(errors.rating) && "error"}
                              help={(errors.rating) ? errors.rating.message : ''} className="film-form-row">
                        <input
                            placeholder="Enter Movie Rating"
                            style={{fontSize: '16px', width: '100%'}}
                            name="rating"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            ref={register}
                        />
                    </FormItem>
                    <FormItem validateStatus={(errors.ticketPrice) && "error"}
                              help={(errors.ticketPrice) ? errors.ticketPrice.message : ''} className="film-form-row">
                        <input
                            placeholder="Enter Ticket Price"
                            style={{fontSize: '16px', width: '100%'}}
                            name="ticketPrice"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            ref={register}
                        />
                    </FormItem>
                    <FormItem validateStatus={(errors.country) && "error"}
                              help={(errors.country) ? errors.country.message : ''} className="film-form-row">
                        <input
                            placeholder="Enter Country"
                            style={{fontSize: '16px', width: '100%'}}
                            name="country"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            ref={register}
                        />
                    </FormItem>
                    <FormItem validateStatus={(errors.genere) && "error"}
                              help={(errors.genere) ? errors.genere.message : ''} className="film-form-row">
                        <input
                            placeholder="Genre"
                            style={{fontSize: '16px', width: '100%'}}
                            name="genere"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            ref={register}
                        />
                    </FormItem>
                    <input
                        name="photo"
                        type='hidden'
                        onChange={handleChange}
                        onBlur={handleBlur}
                        ref={register}
                    />
                    <FormItem className="film-form-row">
                        <textarea validateStatus={(errors.description) && "error"}
                                  help={(errors.description) ? errors.description.message : ''}
                                  placeholder="Enter Movie Description"
                                  style={{fontSize: '16px', width: '100%'}}
                                  autosize={{minRows: 3, maxRows: 6}}
                                  name="description"
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  ref={register}
                                  className="textarea"
                        />
                    </FormItem>
                    <FormItem className="film-form-row">
                        <IssuePhotoUpload callbackArray={callbackArray} callback={callback} success={handleChangeStatus}
                                          removedfile={removedfile}/>
                    </FormItem>
                    <FormItem className="film-form-row">
                        <Button type="primary"
                                htmlType="submit"
                                size="large"
                                className="create-film-form-button">Create Film</Button>
                    </FormItem>
                </Form>
            </div>
        </div>

    );
};

export default NewFilm;