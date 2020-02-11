<template>
    <div>
        <label>File
            <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
        </label>
        <button v-on:click="submitFile()">Submit</button>
    </div>
</template>

<script>

    import {AXIOS} from "../AXIOS/http-common";
    export default {
        name: "UploadFile",
        /*
          Defines the data used by the component
        */
        data(){
            return {
                file: ''
            }
        },
        methods: {
            /*
              Submits the file to the server
            */
            submitFile(){
                /*
                        Initialize the form data
                    */
                let formData = new FormData();
                /*
                    Add the form data we need to submit
                */
                formData.append('file', this.file);
                /*
                  Make the request to the POST /single-file URL
                */
                AXIOS.post( '/single-file',
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                ).then(function(){
                    console.log('SUCCESS!!');
                })
                    .catch(function(){
                        console.log('FAILURE!!');
                    });
            },
            /*
              Handles a change on the file upload
            */
            handleFileUpload(){
                this.file = this.$refs.file.files[0];
            }
        }
    }
</script>

<style scoped>
    .el-header {
        margin: auto;
        display: block;
        padding: 10px;
    }
    .el-menu-item {
        text-decoration: none;
    }
</style>