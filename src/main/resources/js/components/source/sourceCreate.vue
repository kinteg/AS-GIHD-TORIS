<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Создание</p>
        <div>
            <el-tabs v-model="activeName">
                <el-tab-pane label="Источник" name="sourceInfo">
                    <el-row :gutter="20">
                        <el-col :span="6">
                            <div class="grid-content bg-purple">
                                <el-form :model="source" :rules="rules" ref="source" :label-position="labelPosition" label-width="100px">
                                    <el-form-item prop="name" label="Поставщик данных">
                                        <el-input  v-model="source.name"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="longName" label="Полное наименование набора">
                                        <el-input v-model="source.longName"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="shortName" label="Краткое наименование набора">
                                        <el-input v-model="source.shortName"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                        <el-col :span="6">
                            <div class="grid-content bg-purple">
                                <el-form :model="source" :rules="rules" ref="source" :label-position="labelPosition" label-width="100px">
                                    <el-form-item prop="description" label="Описание">
                                        <el-input v-model="source.description"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="addDescription" label="Дополнительное описание">
                                        <el-input v-model="source.addDescription"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="scope" label="Сфера (направление)">
                                        <el-input v-model="source.scope"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                        <el-col :span="6">
                            <div class="grid-content bg-purple">
                                <el-form :model="source" :rules="rules" ref="source" :label-position="labelPosition" label-width="100px">
                                    <el-form-item prop="periodicity" label="Периодичность актуализации">
                                        <el-input v-model="source.periodicity"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="renewalPeriod" label="Срок обновления набора данных">
                                        <el-input v-model="source.renewalPeriod"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="type" label="Вид набора">
                                        <el-input v-model="source.type"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                        <el-col :span="6">
                            <div class="grid-content bg-purple">
                                <el-form :model="source" :rules="rules" ref="source" :label-position="labelPosition" label-width="100px">
                                    <el-form-item prop="tags" label="Ключевые слова (теги)">
                                        <el-input v-model="source.tags"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="providerLink" label="Источник данных">
                                        <el-input v-model="source.providerLink"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="dataSource" label="Ссылка на данные">
                                        <el-input v-model="source.dataSource"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                    </el-row>
                    <el-button @click="createSource" style="background-color: #1ab394; border-color: #1ab394; color: white;">Добавить источник</el-button>
                </el-tab-pane>
                <el-tab-pane label="Шаблоны" name="patternInfo">
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <div>
                                <el-form :model="pattern" :rules="rules" ref="pattern" :label-position="labelPosition" label-width="100px">
                                    <el-form-item prop="name" label="Название">
                                        <el-input v-model="pattern.name"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="description" label="Описание">
                                        <el-input v-model="pattern.description"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div>
                                <el-form :model="pattern" :rules="rules" ref="pattern" :label-position="labelPosition" label-width="100px">
                                    <el-form-item prop="direction" label="Направление:">
                                        <el-input v-model="pattern.direction"></el-input>
                                    </el-form-item>
                                    <el-form-item prop="management" label="Отвтественный за ведение:">
                                        <el-input v-model="pattern.management"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                    </el-row>
                    <el-button @click="createPattern" style="background-color: #1ab394; border-color: #1ab394; color: white;">Добавить шаблон</el-button>
                </el-tab-pane>
            </el-tabs>
            <el-button @click="back" style="margin-top: 10px; background-color: #1ab394; border-color: #1ab394; color: white;">Назад</el-button>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";
    import router from "../../router/router";
    export default {
        name: "sourceCreate",
        data(){
            return{
                activeName: "sourceInfo",
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
                    tags: "",
                    providerLink:"",
                    dataSource:"",
                },
                pattern: {
                    name: "",
                    description: "",
                    direction: "",
                    management: "",
                    sourceId: "",
                },
                rules: {
                    name: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    longName: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    shortName: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    description: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    addDescription: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    scope: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    periodicity: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    renewalPeriod: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    type: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    tags: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    providerLink: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    dataSource: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    patternName: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    patternDescription: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    direction: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    management: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                }
            }
        },
        methods:{
            back(){
                this.$confirm('Уверены что хотите вернуться?', 'Назад', {
                    confirmButtonText: 'Да',
                    cancelButtonText: 'Нет',
                    type: 'warning'
                }).then(() => {
                    router.push({name:'show'});
                }).catch(() => {

                });
            },

            createSource() {
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
                AXIOS.post("/source/create",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                ).then(response => {
                    if(response.data.longName == null){
                        this.noticeError('Ошибка при создании источника.');
                    } else {
                        this.pattern.sourceId = response.data.id;
                        console.log(this.pattern.sourceId);
                        this.noticeSuccess(' Источник  "' + response.data.name + '" успешно создан.');
                    }
                });
            },

            createPattern() {
                let formData = new FormData();
                formData.append("name",this.pattern.name);
                formData.append("description",this.pattern.description);
                formData.append("direction",this.pattern.direction);
                formData.append("management",this.pattern.management);
                formData.append("sourceId",this.pattern.sourceId);
                AXIOS.post("/pattern/create",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                ).then(response => {
                    if(response.data.name == null){
                        console.log(this.pattern.sourceId);
                        this.noticeError('Ошибка при создании шаблона.');
                    } else {
                        console.log(this.pattern.sourceId);
                        this.noticeSuccess('Шаблон "' + response.data.name + '" успешно создан.');
                    }
                });
            },

            noticeError(message) {
                this.$notify({
                    title: 'Ошибка',
                    message:message ,
                    type: 'error',
                });
            },

            noticeSuccess(message) {
                this.$notify({
                    title: 'Успешно',
                    message: message,
                    type: 'success',
                });
            },
        }

    }
</script>

<style scoped>

</style>