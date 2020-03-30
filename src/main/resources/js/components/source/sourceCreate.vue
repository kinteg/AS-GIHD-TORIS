<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Просмотр источника</p>
        <div>
            <el-tabs v-model="activeName">
                <el-tab-pane label="Источник" name="sourceInfo">
                    <el-row :gutter="20">
                        <el-col :span="6">
                            <div class="grid-content bg-purple">
                                <el-form :label-position="labelPosition" label-width="100px" :model="source">
                                    <el-form-item label="Поставщик данных">
                                        <el-input v-model="source.name"></el-input>
                                    </el-form-item>
                                    <el-form-item label="Полное наименование набора">
                                        <el-input v-model="source.longName"></el-input>
                                    </el-form-item>
                                    <el-form-item label="Краткое наименование набора">
                                        <el-input v-model="source.shortName"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                        <el-col :span="6">
                            <div class="grid-content bg-purple">
                                <el-form :label-position="labelPosition" label-width="100px" :model="source">
                                    <el-form-item label="Описание">
                                        <el-input v-model="source.description"></el-input>
                                    </el-form-item>
                                    <el-form-item label="Дополнительное описание">
                                        <el-input v-model="source.addDescription"></el-input>
                                    </el-form-item>
                                    <el-form-item label="Сфера (направление)">
                                        <el-input v-model="source.scope"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                        <el-col :span="6">
                            <div class="grid-content bg-purple">
                                <el-form :label-position="labelPosition" label-width="100px" :model="source">
                                    <el-form-item label="Периодичность актуализации">
                                        <el-input v-model="source.periodicity"></el-input>
                                    </el-form-item>
                                    <el-form-item label="Срок обновления набора данных">
                                        <el-input v-model="source.renewalPeriod"></el-input>
                                    </el-form-item>
                                    <el-form-item label="Вид набора">
                                        <el-input v-model="source.type"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                        <el-col :span="6">
                            <div class="grid-content bg-purple">
                                <el-form :label-position="labelPosition" label-width="100px" :model="source">
                                    <el-form-item label="Ключевые слова (теги)">
                                        <el-input v-model="source.tags"></el-input>
                                    </el-form-item>
                                    <el-form-item label="Источник данных">
                                        <el-input v-model="source.providerLink"></el-input>
                                    </el-form-item>
                                    <el-form-item label="Ссылка на данные на сайте поставщика">
                                        <el-input v-model="source.dataSource"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                    </el-row>
                </el-tab-pane>
                <el-tab-pane label="Шаблоны" name="patternInfo">Шаблоны</el-tab-pane>
            </el-tabs>
        </div>
        <el-button @click="createSource" style="background-color: #1ab394; border-color: #1ab394; color: white;">Добавить</el-button>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";
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
                AXIOS.post("/source/create",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                ).then(response => {
                    if(response.data.longName == null){
                        this.noticeWarning();
                    } else {
                        this.noticeSuccess(response.data.name);
                    }
                });
            },

            noticeWarning(){
                this.$notify({
                    title: 'Ошибка',
                    message: 'Ошибка при создании источника.',
                    type: 'error',
                    duration: 0
                });
            },

            noticeSuccess(name){
                this.$notify({
                    title: 'Успешно',
                    message: 'Источник "' + name + '" успешно создан.',
                    type: 'success',
                    duration: 0
                });
            },
        },
        routeUpdate(to, from) {
            alert("asd");
        }

    }
</script>

<style scoped>

</style>