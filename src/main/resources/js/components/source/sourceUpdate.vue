<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Просмотр</p>
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
                                    <el-form-item prop="dataSource" label="Ссылка на данные на сайте поставщика">
                                        <el-input v-model="source.dataSource"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                    </el-row>
                    <el-button @click="updateSource" style="background-color: #1ab394; border-color: #1ab394; color: white;">Изменить</el-button>
                </el-tab-pane>
                <el-tab-pane label="Шаблоны" name="patternInfo">
                    <div v-if="hiddenTable"  style="background-color: white; padding: 0 5px 0 0;  border-radius: 5px; " >
                        <p style="font-size: 20px">Все шаблоны
                            <el-button class="plus" @click="addPattern" style="float: right; margin-bottom: 15px; background-color: #1ab394; border-color: #1ab394 "  type="primary" icon="el-icon-plus"></el-button>
                        </p>
                        <table style="display: block; overflow-x: auto; ">
                            <tr>
                                <th></th>
                                <th @click="sort('id')">Номер</th>
                                <th @click="sort('name')">Навание</th>
                                <th @click="sort('description')">Описание</th>
                                <th @click="sort('direction')">Направление </th>
                                <th @click="sort('management')">Ответсвенный за ведение </th>
                                <th @click="sort('archive')">Архивность</th>
                                <th @click="sort('date_creation')">Дата создания</th>
                                <th @click="sort('date_deactivation')">Дата деактивации</th>
                                <th @click="sort('date_activation')">Дата активации</th>
                                <th @click="sort('last_update')">Последнее обновление</th>
                            </tr>
                            <tr>
                                <td><el-button @click="sort('')"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-search"></el-button></td>
                                <td><el-input placeholder="Please input" v-model="pattern.id"></el-input></td>
                                <td><el-input placeholder="Please input" v-model="pattern.name"></el-input></td>
                                <td><el-input placeholder="Please input" v-model="pattern.description"></el-input></td>
                                <td><el-input placeholder="Please input" v-model="pattern.direction"></el-input></td>
                                <td><el-input placeholder="Please input" v-model="pattern.management"></el-input></td>
                                <td>
                                    <el-select v-model="value" placeholder="Select">
                                        <el-option
                                                v-for="item in options"
                                                :key="item.value"
                                                :label="item.label"
                                                :value="item.value">
                                        </el-option>
                                    </el-select>
                                </td>
                                <td> <div class="block">
                                    <el-date-picker
                                            value-format="yyyy-MM-dd"
                                            v-model="pattern.dateCreation"
                                            type="daterange"
                                            range-separator="To"
                                            start-placeholder="Start date"
                                            end-placeholder="End date">
                                    </el-date-picker>
                                </div></td>
                                <td> <div class="block">
                                    <el-date-picker
                                            value-format="yyyy-MM-dd"
                                            v-model="pattern.dateDeactivation"
                                            type="daterange"
                                            range-separator="To"
                                            start-placeholder="Start date"
                                            end-placeholder="End date">
                                    </el-date-picker>
                                </div></td>
                                <td> <div class="block">
                                    <el-date-picker
                                            value-format="yyyy-MM-dd"
                                            v-model="pattern.dateActivation"
                                            type="daterange"
                                            range-separator="To"
                                            start-placeholder="Start date"
                                            end-placeholder="End date">
                                    </el-date-picker>
                                </div></td>
                                <td> <div class="block">
                                    <el-date-picker
                                            value-format="yyyy-MM-dd"
                                            v-model="pattern.lastUpdate"
                                            type="daterange"
                                            range-separator="To"
                                            start-placeholder="Start date"
                                            end-placeholder="End date">
                                    </el-date-picker>
                                </div></td>
                            </tr>
                            <tbody v-for="pattern in patternData">
                            <tr>
                                <td>
                                    <span v-if="pattern.isArchive">
                                        <el-button @click="deArchiveOnePattern(pattern.id)"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-upload2"></el-button>
                                    </span>
                                    <span v-else>
                                        <el-button @click="deleteOnePattern(pattern.id)"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-delete"></el-button>
                                    </span>
                                    <br>
                                    <el-button @click="openPatternUpdate(pattern.id)" style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394" type="primary" size="mini" icon="el-icon-edit"></el-button>
                                    <br>
                                    <el-button @click="patternView(pattern.id)" style="background-color: #1ab394; border-color: #1ab394" type="primary" size="mini" icon="el-icon-view"></el-button>
                                </td>
                                <td>{{pattern.id}}</td>
                                <td>{{pattern.name}}</td>
                                <td>{{pattern.description}}</td>
                                <td>{{pattern.direction}}</td>
                                <td>{{pattern.management}}</td>
                                <td>{{pattern.isArchive ? "Да" : "Нет"}}</td>
                                <td>{{pattern.dateCreation}}</td>
                                <td>{{pattern.dateDeactivation}}</td>
                                <td>{{pattern.dateActivation}}</td>
                                <td>{{pattern.lastUpdate}}</td>
                            </tr>
                            </tbody>
                        </table>
                        <my-pagination
                                :page-size="pagination.pageSize"
                                :current-page="pagination.currentPage"
                                :totalPages="pagination.totalPages"
                                :totalElements="pagination.totalElements"
                                @onCurrentChange="onCurrentChange"
                                @onSizeChange="onSizeChange"/>
                    </div>
                    <div v-else-if="hiddenUpdate">
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
                        <el-button @click="updatePattern" style="background-color: #1ab394; border-color: #1ab394; color: white;">Сохранить</el-button>
                    </div>
                    <div v-if="hiddenView">
                        <pattern-view :pattern-id="this.patternId" />
                        <el-button @click="backView" style="background-color: #1ab394; border-color: #1ab394; color: white;">Назад</el-button>
                    </div>
                    <div v-else-if="hiddenAdd">
                        <pattern-create :source-id="this.sourceId"/>
                        <br>
                        <el-button @click="backAdd" style="background-color: #1ab394; border-color: #1ab394; color: white;">Назад</el-button>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";
    import router from "../../router/router";
    import PatternView from "../pattern/patternView.vue";
    import PatternCreate from "../pattern/patternCreate.vue";
    import MyPagination from "../general/pagination.vue";
    export default {
        name: "sourceCreate",
        components: {MyPagination, PatternCreate, PatternView},
        props:['id'],
        data() {
            return {
                options: [{
                    value: '',
                    label: ''
                }, {
                    value: 'true',
                    label: 'Да'
                }, {
                    value: false,
                    label: 'Нет'
                }],
                value: '',
                pickerOptions: {
                    shortcuts: [{
                        text: 'Last week',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: 'Last month',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: 'Last 3 months',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        }
                    }]
                },
                sourceId:"",
                patternId: "",
                patternData:[],
                onePatternData:[],
                hiddenTable: true,
                hiddenAdd: false,
                hiddenView: false,
                hiddenUpdate: false,
                labelPosition:"top",
                activeName: "sourceInfo",
                pagination:{
                    pageSize: 10,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                },
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
                    sourceId: "",
                    archiveFileCount: "",
                    description: "",
                    direction: "",
                    management: "",
                    isArchive: "",
                    dateCreation: "",
                    dateCreation1: "",
                    dateCreation2: "",
                    dateDeactivation: "",
                    dateDeactivation1: "",
                    dateDeactivation2: "",
                    dateActivation: "",
                    dateActivation1: "",
                    dateActivation2: "",
                    lastUpdate: "",
                    lastUpdate1: "",
                    lastUpdate2: "",
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
            notify(title,message,type) {
                this.$notify({
                    title: title,
                    message: message,
                    type: type
                });
            },

            sort(key){
                if(this.pattern.dateCreation !== null && this.pattern.dateCreation !== "") {
                    this.pattern.dateCreation1 = this.pattern.dateCreation[0];
                    this.pattern.dateCreation2 = this.pattern.dateCreation[1];
                } else {
                    this.pattern.dateCreation1 = "";
                    this.pattern.dateCreation2 = "";
                }

                if(this.pattern.dateDeactivation !== null && this.pattern.dateDeactivation !== "") {
                    this.pattern.dateDeactivation1 = this.pattern.dateDeactivation[0];
                    this.pattern.dateDeactivation2 = this.pattern.dateDeactivation[1];
                } else {
                    this.pattern.dateDeactivation1 = "";
                    this.pattern.dateDeactivation2 = "";
                }

                if(this.pattern.dateActivation !== null && this.pattern.dateActivation !== "") {
                    this.pattern.dateActivation1 = this.pattern.dateActivation[0];
                    this.pattern.dateActivation2 = this.pattern.dateActivation[1];
                } else {
                    this.pattern.dateActivation1 = "";
                    this.pattern.dateActivation2 = "";
                }

                if(this.pattern.lastUpdate !== null && this.pattern.lastUpdate !== "") {
                    this.pattern.lastUpdate1 = this.pattern.lastUpdate[0];
                    this.pattern.lastUpdate2 = this.pattern.lastUpdate[1];
                } else {
                    this.pattern.lastUpdate1 = "";
                    this.pattern.lastUpdate2 = "";
                }

                if(this.pattern.key === key ) {
                    switch(this.pattern.sort) {
                        case "":
                            this.pattern.sort = "asc";
                            break;
                        case "asc":
                            this.pattern.sort = "desc";
                            break;
                        case "desc":
                            this.pattern.sort = "";
                            break;
                    }
                }
                else {
                    this.pattern.key = key;
                    this.pattern.sort = "asc";
                }
                let formData = new FormData();
                formData.append("isArchive",this.value);
                formData.append("sort",this.pattern.sort);
                formData.append("key",this.pattern.key);
                formData.append("id",this.pattern.id);
                formData.append("name",this.pattern.name);
                formData.append("direction",this.pattern.direction);
                formData.append("description",this.pattern.description);
                formData.append("management",this.pattern.management);
                formData.append("periodicity",this.pattern.periodicity);
                formData.append("sourceId",this.pattern.sourceId);
                formData.append("dateCreation1",this.pattern.dateCreation1);
                formData.append("dateCreation2",this.pattern.dateCreation2);
                formData.append("dateDeactivation1",this.pattern.dateDeactivation1);
                formData.append("dateDeactivation2",this.pattern.dateDeactivation2);
                formData.append("dateActivation1",this.pattern.dateActivation1);
                formData.append("dateActivation2",this.pattern.dateActivation2);
                formData.append("lastUpdate1",this.pattern.lastUpdate1);
                formData.append("lastUpdate2",this.pattern.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);

                AXIOS.post("/pattern/getAllSort",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    this.pagination.totalPages = response.data.totalPages;
                    this.pagination.totalElements = response.data.totalElements;
                    this.patternData = response.data.content;
                })
            },

            onCurrentChange(value) {
                this.pagination.currentPage = value;

                if(this.pattern.dateCreation !== null && this.pattern.dateCreation !== "") {
                    this.pattern.dateCreation1 = this.pattern.dateCreation[0];
                    this.pattern.dateCreation2 = this.pattern.dateCreation[1];
                } else {
                    this.pattern.dateCreation1 = "";
                    this.pattern.dateCreation2 = "";
                }

                if(this.pattern.dateDeactivation !== null && this.pattern.dateDeactivation !== "") {
                    this.pattern.dateDeactivation1 = this.pattern.dateDeactivation[0];
                    this.pattern.dateDeactivation2 = this.pattern.dateDeactivation[1];
                } else {
                    this.pattern.dateDeactivation1 = "";
                    this.pattern.dateDeactivation2 = "";
                }

                if(this.pattern.dateActivation !== null && this.pattern.dateActivation !== "") {
                    this.pattern.dateActivation1 = this.pattern.dateActivation[0];
                    this.pattern.dateActivation2 = this.pattern.dateActivation[1];
                } else {
                    this.pattern.dateActivation1 = "";
                    this.pattern.dateActivation2 = "";
                }

                if(this.pattern.lastUpdate !== null && this.pattern.lastUpdate !== "") {
                    this.pattern.lastUpdate1 = this.pattern.lastUpdate[0];
                    this.pattern.lastUpdate2 = this.pattern.lastUpdate[1];
                } else {
                    this.pattern.lastUpdate1 = "";
                    this.pattern.lastUpdate2 = "";
                }

                let formData = new FormData();
                formData.append("isArchive",this.value);
                formData.append("sort",this.pattern.sort);
                formData.append("key",this.pattern.key);
                formData.append("id",this.pattern.id);
                formData.append("name",this.pattern.name);
                formData.append("description",this.pattern.description);
                formData.append("management",this.pattern.management);
                formData.append("periodicity",this.pattern.periodicity);
                formData.append("dateCreation1",this.pattern.dateCreation1);
                formData.append("dateCreation2",this.pattern.dateCreation2);
                formData.append("dateDeactivation1",this.pattern.dateDeactivation1);
                formData.append("dateDeactivation2",this.pattern.dateDeactivation2);
                formData.append("dateActivation1",this.pattern.dateActivation1);
                formData.append("dateActivation2",this.pattern.dateActivation2);
                formData.append("lastUpdate1",this.pattern.lastUpdate1);
                formData.append("lastUpdate2",this.pattern.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);

                AXIOS.post("/pattern/getAllSort", formData)
                    .then(response => {
                        this.patternData = response.data.content;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            },

            onSizeChange(value) {
                if(this.pattern.dateCreation !== null && this.pattern.dateCreation !== "") {
                    this.pattern.dateCreation1 = this.pattern.dateCreation[0];
                    this.pattern.dateCreation2 = this.pattern.dateCreation[1];
                } else {
                    this.pattern.dateCreation1 = "";
                    this.pattern.dateCreation2 = "";
                }

                if(this.pattern.dateDeactivation !== null && this.pattern.dateDeactivation !== "") {
                    this.pattern.dateDeactivation1 = this.pattern.dateDeactivation[0];
                    this.pattern.dateDeactivation2 = this.pattern.dateDeactivation[1];
                } else {
                    this.pattern.dateDeactivation1 = "";
                    this.pattern.dateDeactivation2 = "";
                }

                if(this.pattern.dateActivation !== null && this.pattern.dateActivation !== "") {
                    this.pattern.dateActivation1 = this.pattern.dateActivation[0];
                    this.pattern.dateActivation2 = this.pattern.dateActivation[1];
                } else {
                    this.pattern.dateActivation1 = "";
                    this.pattern.dateActivation2 = "";
                }

                if(this.pattern.lastUpdate !== null && this.pattern.lastUpdate !== "") {
                    this.pattern.lastUpdate1 = this.pattern.lastUpdate[0];
                    this.pattern.lastUpdate2 = this.pattern.lastUpdate[1];
                } else {
                    this.pattern.lastUpdate1 = "";
                    this.pattern.lastUpdate2 = "";
                }

                this.pagination.pageSize = value;
                this.pagination.currentPage = 1;

                let formData = new FormData();
                formData.append("isArchive",this.value);
                formData.append("sort",this.pattern.sort);
                formData.append("key",this.pattern.key);
                formData.append("id",this.pattern.id);
                formData.append("name",this.pattern.name);
                formData.append("description",this.pattern.description);
                formData.append("management",this.pattern.management);
                formData.append("periodicity",this.pattern.periodicity);
                formData.append("dateCreation1",this.pattern.dateCreation1);
                formData.append("dateCreation2",this.pattern.dateCreation2);
                formData.append("dateDeactivation1",this.pattern.dateDeactivation1);
                formData.append("dateDeactivation2",this.pattern.dateDeactivation2);
                formData.append("dateActivation1",this.pattern.dateActivation1);
                formData.append("dateActivation2",this.pattern.dateActivation2);
                formData.append("lastUpdate1",this.pattern.lastUpdate1);
                formData.append("lastUpdate2",this.pattern.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);

                AXIOS.post("/pattern/getAllSort", formData)
                    .then(response => {
                        this.patternData = response.data.content;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            },

            deletePattern(id) {
                AXIOS.get("pattern/archive/" + id).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Шаблон был архивирован','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Шаблон не был архивирован','error');
                    }
                });
            },

            deleteOnePattern(id) {
                this.deletePattern(id);
            },

            deArchivePattern(id){
                AXIOS.get("pattern/deArchive/" + id).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Шаблон был активирован','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Шаблон не был активирован','error');
                    }
                });
            },

            deArchiveOnePattern(id){
                this.deArchivePattern(id);
            },

            patternView(id) {
                this.hiddenView = true;
                this.hiddenTable = false;
                this.hiddenAdd = false;
                this.patternId = id;

            },

            backView() {
                this.hiddenTable = true;
                this.hiddenView = false;
                this.hiddenAdd = false;
                this.patternId = "";
                this.updatePage();
            },

            addPattern(){
                this.hiddenAdd = true;
                this.hiddenTable = false;
                this.hiddenView = false;
                this.sourceId = this.$route.params.id;
            },

            backAdd(){
                this.$confirm('Уверены что хотите вернуться?', 'Назад', {
                    confirmButtonText: 'Да',
                    cancelButtonText: 'Нет',
                    type: 'warning'
                }).then(() => {
                    this.pattern = "";
                    this.hiddenTable = true;
                    this.hiddenView = false;
                    this.hiddenAdd = false;
                    this.updatePage();

                }).catch(() => {

                });
            },

            backUpdate() {
                this.$confirm('Уверены что хотите вернуться?', 'Назад', {
                    confirmButtonText: 'Да',
                    cancelButtonText: 'Нет',
                    type: 'warning'
                }).then(() => {
                    this.pattern = "";
                    this.hiddenTable = true;
                    this.hiddenUpdate = false;
                    this.updatePage();
                }).catch(() => {

                });
            },

            openPatternUpdate(id) {
                AXIOS.get("pattern/" + id).then(response => {
                    this.hiddenTable = false;
                    this.hiddenUpdate = true;
                    this.pattern = response.data;
                })
            },

            updatePattern() {
                let formData = new FormData();
                formData.append("id",this.pattern.id);
                formData.append("name",this.pattern.name);
                formData.append("fileCount",this.pattern.fileCount);
                formData.append("archiveFileCount",this.pattern.archiveFileCount);
                formData.append("description",this.pattern.description);
                formData.append("direction",this.pattern.direction);
                formData.append("management",this.pattern.management);
                formData.append("isArchive",this.pattern.isArchive);
                formData.append("sourceId",this.pattern.sourceId);
                formData.append("dateCreation",this.pattern.dateCreation);
                formData.append("dateDeactivation",this.pattern.dateDeactivation);
                formData.append("dateActivation",this.pattern.dateActivation);
                // formData.append("lastUpdate",this.pattern.lastUpdate);
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

            updateSource() {
                let formData = new FormData();
                formData.append("id",this.source.id);
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
                formData.append("isArchive",this.source.isArchive);
                formData.append("dateCreation",this.source.dateCreation);
                formData.append("dateDeactivation",this.source.dateDeactivation);
                formData.append("dateActivation",this.source.dateActivation);
                formData.append("lastUpdate",this.source.lastUpdate);
                AXIOS.post("/source/update",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                ).then(response => {
                    if(response.data.longName == null){
                        this.notify("Ошибка","Ошибка при изменении источника.","error");
                    } else {
                        this.notify("Успешно",'Источник "' + response.data.name + '" успешно изменен.',"success");
                    }
                });

                router.push({name:'show'});
            },

            updatePage(){
                AXIOS.get("pattern/getAll/" + this.$route.params.id).then(response => {
                    this.patternData = response.data.content;
                })
            }
        },

        mounted() {
            AXIOS.get("source/" + this.$route.params.id).then(response => {
                this.source = response.data;
            });

            AXIOS.get("pattern/getAll/" + this.$route.params.id).then(response => {
                this.patternData = response.data.content;
            })
        }
    }
</script>

<style scoped>
    table, td, th {
        border: 1px solid #d7d7d7;
        text-align: center;
    }

    td{
        padding: 10px;
    }

    table {
        border-collapse: collapse;
        width: 100%;
    }

    th {
        padding: 10px;
        height: 50px;
    }
</style>