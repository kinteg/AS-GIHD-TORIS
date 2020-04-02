<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Просмотр</p>
        <div>
            <el-tabs v-model="activeName">
                <el-tab-pane label="Источник" name="sourceInfo">
                    <div>
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <div>
                                    <el-form :label-position="labelPosition" label-width="100px" :model="source">
                                        <el-form-item label="Поставщик данных:">
                                            {{source.name}}
                                        </el-form-item>
                                        <el-form-item label="Полное наименование набора:">
                                            {{source.longName}}
                                        </el-form-item>
                                        <el-form-item label="Краткое наименование набора:">
                                            {{source.shortName}}
                                        </el-form-item>
                                        <el-form-item label="Описание:">
                                            {{source.description}}
                                        </el-form-item>
                                        <el-form-item label="Дополнительное описание:">
                                            {{source.addDescription}}
                                        </el-form-item>
                                        <el-form-item label="Сфера (направление):">
                                            {{source.scope}}
                                        </el-form-item>
                                    </el-form>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div>
                                    <el-form :label-position="labelPosition" label-width="100px" :model="source">
                                        <el-form-item label="Периодичность актуализации:">
                                            {{source.periodicity}}
                                        </el-form-item>
                                        <el-form-item label="Срок обновления набора данных:">
                                            {{source.renewalPeriod}}
                                        </el-form-item>
                                        <el-form-item label="Вид набора:">
                                            {{source.type}}
                                        </el-form-item>
                                        <el-form-item label="Ключевые слова (теги):">
                                            {{source.tags}}
                                        </el-form-item>
                                        <el-form-item label="Источник данных:">
                                            {{source.providerLink}}
                                        </el-form-item>
                                        <el-form-item label="Ссылка на данные на сайте поставщика:">
                                            {{source.dataSource}}
                                        </el-form-item>
                                    </el-form>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="Шаблон" name="patternInfo">
                    <div v-if="viewPattern" >
                        <p style="font-size: 20px">Просмотр шаблона</p>
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <div>
                                    <el-form :label-position="labelPosition" label-width="100px" :model="pattern">
                                        <el-form-item label="Название:">
                                            {{pattern.name}}
                                        </el-form-item>
                                        <el-form-item label="Описание:">
                                            {{pattern.description}}
                                        </el-form-item>
                                        <el-form-item label="Сфера (направление):">
                                            {{pattern.direction}}
                                        </el-form-item>
                                    </el-form>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <el-form :label-position="labelPosition" label-width="100px" :model="pattern">
                                        <el-form-item label="Ответственный за ведение:">
                                            {{pattern.management}}
                                        </el-form-item>
                                        <el-form-item label="Архивность:">
                                            {{pattern.isArchive ? "Да" : "Нет"}}
                                        </el-form-item>
                                        <el-form-item label="Дата создания:">
                                            {{pattern.dateCreation}}
                                        </el-form-item>
                                    </el-form>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <el-form :label-position="labelPosition" label-width="100px" :model="pattern">
                                        <el-form-item label="Дата архивации:">
                                            {{pattern.dateDeactivation}}
                                        </el-form-item>
                                        <el-form-item label="Дата активации:">
                                            {{pattern.dateActivation}}
                                        </el-form-item>
                                        <el-form-item label="Последнее обновление:">
                                            {{pattern.lastUpdate}}
                                        </el-form-item>
                                    </el-form>
                                </div>
                            </el-col>
                        </el-row>
                        <el-button @click="updatePattern"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  >Редактировать</el-button>
                    </div>
                    <div v-else >
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
                        <el-button @click="backUpdate" style="background-color: #1ab394; border-color: #1ab394; color: white;">Назад</el-button>
                        <el-button @click="updatePatternAccept" style="background-color: #1ab394; border-color: #1ab394; color: white;">Сохранить</el-button>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="Таблицы" name="tableInfo">
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";

    export default {
        name: "card",
        data(){
            return{
                labelPosition: "top",
                activeName: "patternInfo",
                patternData:"",
                viewPattern: true,
                patternId:"",
                sourceId:"",
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
                    tags:"",
                    providerLink:"",
                    dataSource:"",
                },
                pattern: {
                    check: [],
                    key: "id",
                    sort: "",
                    id: "",
                    name: "",
                    fileCount: "",
                    archiveFileCount: "",
                    description: "",
                    direction: "",
                    management: "",
                    isArchive: "",
                    dateCreation: "",
                    dateDeactivation: "",
                    dateActivation: "",
                    lastUpdate: "",
                },
                rules: {
                    name: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    description: [
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
            notify(title,message,type) {
                this.$notify({
                    title: title,
                    message: message,
                    type: type
                });
            },
            backUpdate(){
                this.$confirm('Уверены что хотите вернуться?', 'Назад', {
                    confirmButtonText: 'Да',
                    cancelButtonText: 'Нет',
                    type: 'warning'
                }).then(() => {
                    this.pattern = "";
                    this.viewPattern = true;
                    this.updatePage();
                }).catch(() => {

                });
            },

            updatePattern(){
                this.viewPattern = false;
            },

            updatePatternAccept(){
                let formData = new FormData();
                formData.append("id",this.pattern.id);
                formData.append("name",this.pattern.name);
                formData.append("fileCount",this.pattern.fileCount);
                formData.append("archiveFileCount",this.pattern.archiveFileCount);
                formData.append("description",this.pattern.description);
                formData.append("direction",this.pattern.direction);
                formData.append("management",this.pattern.management);
                formData.append("sourceId",this.pattern.sourceId);
                formData.append("isArchive",this.pattern.isArchive);
                formData.append("dateCreation",this.pattern.dateCreation);
                formData.append("dateDeactivation",this.pattern.dateDeactivation);
                formData.append("dateActivation",this.pattern.dateActivation);
                AXIOS.post("/pattern/update",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                ).then(response => {
                    if(response.data.name == null){
                        console.log(this.pattern.sourceId);
                        this.notify("Ошибка","Ошибка при изменении шаблона.","error");
                    } else {
                        this.notify("Успешно",'Шаблон "' + response.data.name + '" успешно изменен.',"success");
                    }
                });
            },

            updatePage(){
                AXIOS.get("pattern/" + this.patternId).then(response => {
                    this.pattern = response.data;
                });
            }

        },
        mounted() {
            this.patternId = this.$route.params.id;
            AXIOS.get("pattern/" + this.patternId).then(response => {
                this.pattern = response.data;
                this.sourceId = response.data.sourceId;
                AXIOS.get("source/" + this.sourceId).then(response => {
                    this.source = response.data;
                });
            });
        }
    }
</script>

<style scoped>

</style>