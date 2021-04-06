
import React from 'react';
import ReactDOM from 'react-dom';
import DropzoneComponent from 'react-dropzone-component';
import {API_BASE_URL,  ACCESS_TOKEN, FILE_UPLOAD_URL} from '../constants';

const IssuePhotoUpload = ({callbackArray, callback, success, removedfile}) => {




    const config = {
        iconFiletypes: ['.jpg', '.png', '.gif'],
        showFiletypeIcon: true,
        postUrl: FILE_UPLOAD_URL,
    };
    const djsConfig = {
        addRemoveLinks: true,
        maxFiles: 1,
        acceptedFiles: "image/jpeg,image/png,image/gif",
        params: {
            myParameter: "Email or some sort of ID will be passed  from here so that image could be mapped with that ID"
        },
        dictDefaultMessage: "Drop your issue images here",
    };

    const eventHandlers = {
        init: dz => this.dropzone = dz,
        drop: callbackArray,
        addedfile: callback,
        success:  success,
        removedfile: removedfile
    }

    return <DropzoneComponent config={config} eventHandlers={eventHandlers} djsConfig={djsConfig}/>

}
export default IssuePhotoUpload;