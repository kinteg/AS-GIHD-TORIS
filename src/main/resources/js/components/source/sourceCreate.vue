<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Добавление источника</p>
        <hr>
        <div>
            <el-form :label-position="labelPosition" label-width="100px" :model="source">
                <el-form-item label="Name">
                    <el-input v-model="source.name"></el-input>
                </el-form-item>
                <el-form-item label="longName">
                    <el-input v-model="source.longName"></el-input>
                </el-form-item>
                <el-form-item label="shortName">
                    <el-input v-model="source.shortName"></el-input>
                </el-form-item>
                <el-form-item label="description">
                    <el-input v-model="source.description"></el-input>
                </el-form-item>
                <el-form-item label="addDescription">
                    <el-input v-model="source.addDescription"></el-input>
                </el-form-item>
                <el-form-item label="scope">
                    <el-input v-model="source.scope"></el-input>
                </el-form-item>
                <el-form-item label="periodicity">
                    <el-input v-model="source.periodicity"></el-input>
                </el-form-item>
                <el-form-item label="renewalPeriod">
                    <el-input v-model="source.renewalPeriod"></el-input>
                </el-form-item>
                <el-form-item label="type">
                    <el-input v-model="source.type"></el-input>
                </el-form-item>
                <el-form-item label="tags">
                    <el-input v-model="source.tags"></el-input>
                </el-form-item>
                <el-form-item label="providerLink ">
                    <el-input v-model="source.providerLink"></el-input>
                </el-form-item>
                <el-form-item label="dataSource">
                    <el-input v-model="source.dataSource"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button @click="createSource" style="background-color: #1ab394; border-color: #1ab394; color: white;">Добавить</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";
    export default {
        name: "sourceCreate",
        data(){
            return{
                labelPosition:"top",
                source:{
                    name:"",
                    longName:"",
                    shortName:"",
                    description:"",
                    addDescription:"",
                    scope:"",
                    periodicity:"",
                    renewalPeriod:"",
                    type:"",
                    providerLink:"",
                    dataSource:"",
                }
            }
        },
        methods:{
            createSource(){
                let formData = new FormData();
                formData.append("name",this.source.name);
                formData.append("longName",this.source.longName);
                formData.append("shortName",this.source.shortName);
                formData.append("description",this.source.description);
                formData.append("addDescription",this.source.addDescription);
                formData.append("scope",this.source.scope);
                formData.append("periodicity",this.source.periodicity);
                formData.append("renewalPeriod",this.source.renewalPeriod);
                formData.append("type",this.source.type);
                formData.append("tags",this.source.tags);
                formData.append("providerLink",this.source.providerLink);
                formData.append("dataSource",this.source.dataSource);
                formData.append("isArchive",false);
                console.log(this.source);
                AXIOS.post("/source/create",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                );
            }
        }
    }
</script>

<style scoped>

</style>