<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Источники
            <el-button @click="deleteSomeSource"  style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-delete"></el-button>
            <el-button @click="addSource" style="float: right; margin-bottom: 15px; background-color: #1ab394; border-color: #1ab394 "  type="primary" icon="el-icon-plus"></el-button>
        </p>
        <div class="horizontal-scroll-wrapper  rectangles">
            <table style="display: block; overflow-x: auto; ">
                <tr>
                    <th></th>
                    <th><el-checkbox ></el-checkbox></th>
                    <th @click="sort('id')">Номер</th>
                    <th @click="sort('name')">Поставщик данных</th>
                    <th @click="sort('long_name')">Полное наименование набора</th>
                    <th @click="sort('short_name')">Краткое наименование набора</th>
                    <th @click="sort('description')">Описание</th>
                    <th @click="sort('add_description')">Дополнительное описание</th>
                    <th @click="sort('scope')">Сфера (направление)</th>
                    <th @click="sort('periodicity')">Периодичность актуализации </th>
                    <th @click="sort('renewal_period')">Срок обновления набора данных</th>
                    <th @click="sort('type')">Вид набора</th>
                    <th @click="sort('tags')">Ключевые слова (теги)</th>
                    <th @click="sort('provider_link')">Информационная система - источник данных</th>
                    <th @click="sort('data_source')">Ссылка на данные на сайте поставщика</th>
                    <th @click="sort('archive')">Архивность</th>
                    <th @click="sort('date_creation')">Дата создания</th>
                    <th @click="sort('date_deactivation')">Дата деактивации</th>
                    <th @click="sort('date_activation')">Дата активации</th>
                    <th @click="sort('last_update')">Последнее обновление</th>
                </tr>
                <tr>
                    <td><el-button @click="sort('')"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-search"></el-button></td>
                    <td></td>
                    <td><el-input placeholder="Please input" v-model="source.id"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.name"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.longName"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.shortName"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.description"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.addDescription"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.scope"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.periodicity"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.renewalPeriod"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.type"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.tags"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.providerLink"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.dataSource"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.isArchive"></el-input></td>
                    <td> <div class="block">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="source.dateCreation"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
                    <td> <div class="block">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="source.dateDeactivation"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
                    <td> <div class="block">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="source.dateActivation"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
                    <td> <div class="block">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="source.lastUpdate"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
                    <td><el-input placeholder="Please input" v-model="source.dateDeactivation"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.dateActivation"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.lastUpdate"></el-input></td>
                </tr>
                <tbody v-for="source in sourceData">
                <tr >
                    <td>
                        <el-button @click="deleteOneSource(source.id)"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-delete"></el-button>
                        <br>
                        <el-button @click="updateSource(source.id)" style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394" type="primary" size="mini" icon="el-icon-edit"></el-button>
                        <br>
                        <el-button @click="sourceView(source.id)" style="background-color: #1ab394; border-color: #1ab394" type="primary" size="mini" icon="el-icon-view"></el-button>
                    </td>
                    <td> <el-checkbox @change="check(source.id)"></el-checkbox></td>
                    <td>{{source.id}}</td>
                    <td>{{source.name}}</td>
                    <td>{{source.longName}}</td>
                    <td>{{source.shortName}}</td>
                    <td>{{source.description}}</td>
                    <td>{{source.addDescription}}</td>
                    <td>{{source.scope}}</td>
                    <td>{{source.periodicity}}</td>
                    <td>{{source.renewalPeriod}}</td>
                    <td>{{source.type}}</td>
                    <td>{{source.tags}}</td>
                    <td>{{source.providerLink}}</td>
                    <td>{{source.dataSource}}</td>
                    <td>{{source.isArchive ? "Да" : "Нет"}}</td>
                    <td>{{source.dateCreation}}</td>
                    <td>{{source.dateDeactivation}}</td>
                    <td>{{source.dateActivation}}</td>
                    <td>{{source.lastUpdate}}</td>
                </tr>
                </tbody>

            </table>
        </div>
        <my-pagination
                :page-size="pagination.pageSize"
                :current-page="pagination.currentPage"
                :totalPages="pagination.totalPages"
                :totalElements="pagination.totalElements"
                @onCurrentChange="onCurrentChange"
                @onSizeChange="onSizeChange"/>
    </div>
</template>

<script>
    import router from "../../router/router";
    import {AXIOS} from "../../AXIOS/http-common";
    import MyPagination from "../general/pagination.vue";
    export default {
        name: "sourceTableNoArchive",
        components: {MyPagination},
        data() {
            return {
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
                url:"source/getAll?",
                sourceData: "",
                pagination:{
                    pageSize: 10,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                },
                source:{
                    check:[],
                    key:"id",
                    sort:"",
                    id:"",
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
                    isArchive:"",
                    dateCreation:"",
                    dateCreation1:"",
                    dateCreation2:"",
                    dateDeactivation:"",
                    dateDeactivation1:"",
                    dateDeactivation2:"",
                    dateActivation:"",
                    dateActivation1:"",
                    dateActivation2:"",
                    lastUpdate:"",
                    lastUpdate1:"",
                    lastUpdate2:"",

                }
            }
        },
        methods:{

            check(id){
                let key = this.source.check.indexOf(id);
                if(key !== -1){
                    this.source.check.splice(key,1);
                } else {
                    this.source.check.push(id);
                }
            },



            deleteSource(id){
                AXIOS.get("source/archive/" + id).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Источник был архивирован','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Источник не был архивирован','error');
                    }
                });
            },

            deleteOneSource(id){
                this.$confirm('Архивировать все связанные с этим источником шаблоны', 'Архивировать', {
                    confirmButtonText: 'OK',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    this.deleteSource(id);
                    AXIOS.get("pattern/archivePatterns/" + id);
                    this.$message({
                        type: 'success',
                        message: 'Источник архивирован вместе с шаблонами'
                    });
                }).catch(() => {
                    this.deleteSource(id);
                    this.$message({
                        type: 'success',
                        message: 'Источник архивирован без шаблонов'
                    });
                });
            },

            deleteSomeSource(){
                this.$confirm('Архивировать все связанные с этим источником шаблоны', 'Архвировать', {
                    confirmButtonText: 'OK',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    if(this.source.check.length !== 0){
                        for(let i = 0; i < this.source.check.length; i++){
                            this.deleteSource(this.source.check[i]);
                        }
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Выберите источники которые хотите архивировать','error');
                    }
                    // AXIOS.get("pattern/archivePatterns/" + i);
                    this.$message({
                        type: 'warning',
                        message: 'Источник архивирован вместе с шаблонами'
                    });

                }).catch(() => {
                    if(this.source.check.length !== 0){
                        for(let i = 0; i < this.source.check.length; i++){
                            this.deleteSource(this.source.check[i]);
                        }
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Выберите источники которые хотите архивировать','error');
                    }
                    this.$message({
                        type: 'success',
                        message: 'Источник архивирован без шаблонов'
                    });
                });

            },

            onCurrentChange(value) {
                this.pagination.currentPage = value;

                if(this.source.dateCreation !== null && this.source.dateCreation !== "") {
                    this.source.dateCreation1 = this.source.dateCreation[0];
                    this.source.dateCreation2 = this.source.dateCreation[1];
                } else {
                    this.source.dateCreation1 = "";
                    this.source.dateCreation2 = "";
                }

                if(this.source.dateDeactivation !== null && this.source.dateDeactivation !== "") {
                    this.source.dateDeactivation1 = this.source.dateDeactivation[0];
                    this.source.dateDeactivation2 = this.source.dateDeactivation[1];
                } else {
                    this.source.dateDeactivation1 = "";
                    this.source.dateDeactivation2 = "";
                }

                if(this.source.dateActivation !== null && this.source.dateActivation !== "") {
                    this.source.dateActivation1 = this.source.dateActivation[0];
                    this.source.dateActivation2 = this.source.dateActivation[1];
                } else {
                    this.source.dateActivation1 = "";
                    this.source.dateActivation2 = "";
                }

                if(this.source.lastUpdate !== null && this.source.lastUpdate !== "") {
                    this.source.lastUpdate1 = this.source.lastUpdate[0];
                    this.source.lastUpdate2 = this.source.lastUpdate[1];
                } else {
                    this.source.lastUpdate1 = "";
                    this.source.lastUpdate2 = "";
                }

                let formData = new FormData();
                formData.append("sort",this.source.sort);
                formData.append("key",this.source.key);
                formData.append("name",this.source.name);
                formData.append("id",this.source.id);
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
                formData.append("dateCreation1",this.source.dateCreation1);
                formData.append("dateCreation2",this.source.dateCreation2);
                formData.append("dateDeactivation1",this.source.dateDeactivation1);
                formData.append("dateDeactivation2",this.source.dateDeactivation2);
                formData.append("dateActivation1",this.source.dateActivation1);
                formData.append("dateActivation2",this.source.dateActivation2);
                formData.append("lastUpdate1",this.source.lastUpdate1);
                formData.append("lastUpdate2",this.source.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);
                AXIOS.post("/source/getAllNotArchiveSort", formData)
                    .then(response => {
                        this.sourceData = response.data.content;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            },

            onSizeChange(value) {
                if(this.source.dateCreation !== null && this.source.dateCreation !== "") {
                    this.source.dateCreation1 = this.source.dateCreation[0];
                    this.source.dateCreation2 = this.source.dateCreation[1];
                } else {
                    this.source.dateCreation1 = "";
                    this.source.dateCreation2 = "";
                }

                if(this.source.dateDeactivation !== null && this.source.dateDeactivation !== "") {
                    this.source.dateDeactivation1 = this.source.dateDeactivation[0];
                    this.source.dateDeactivation2 = this.source.dateDeactivation[1];
                } else {
                    this.source.dateDeactivation1 = "";
                    this.source.dateDeactivation2 = "";
                }

                if(this.source.dateActivation !== null && this.source.dateActivation !== "") {
                    this.source.dateActivation1 = this.source.dateActivation[0];
                    this.source.dateActivation2 = this.source.dateActivation[1];
                } else {
                    this.source.dateActivation1 = "";
                    this.source.dateActivation2 = "";
                }

                if(this.source.lastUpdate !== null && this.source.lastUpdate !== "") {
                    this.source.lastUpdate1 = this.source.lastUpdate[0];
                    this.source.lastUpdate2 = this.source.lastUpdate[1];
                } else {
                    this.source.lastUpdate1 = "";
                    this.source.lastUpdate2 = "";
                }

                this.pagination.pageSize = value;
                this.pagination.currentPage = 1;

                let formData = new FormData();
                formData.append("sort",this.source.sort);
                formData.append("key",this.source.key);
                formData.append("name",this.source.name);
                formData.append("id",this.source.id);
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
                formData.append("dateCreation1",this.source.dateCreation1);
                formData.append("dateCreation2",this.source.dateCreation2);
                formData.append("dateDeactivation1",this.source.dateDeactivation1);
                formData.append("dateDeactivation2",this.source.dateDeactivation2);
                formData.append("dateActivation1",this.source.dateActivation1);
                formData.append("dateActivation2",this.source.dateActivation2);
                formData.append("lastUpdate1",this.source.lastUpdate1);
                formData.append("lastUpdate2",this.source.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);
                AXIOS.post("/source/getAllNotArchiveSort", formData)
                    .then(response => {
                        this.sourceData = response.data.content;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            },

            addSource(){
                router.push('create');
            },

            updateSource(id){
                router.push('update/'+ id);
            },

            sourceView(id){
                router.push('view/'+ id);
            },

            notify(title,message,type){
                this.$notify({
                    title: title,
                    message: message,
                    type: type
                });
            },

            sort(key) {
                if(this.source.dateCreation !== null && this.source.dateCreation !== "") {
                    this.source.dateCreation1 = this.source.dateCreation[0];
                    this.source.dateCreation2 = this.source.dateCreation[1];
                } else {
                    this.source.dateCreation1 = "";
                    this.source.dateCreation2 = "";
                }

                if(this.source.dateDeactivation !== null && this.source.dateDeactivation !== "") {
                    this.source.dateDeactivation1 = this.source.dateDeactivation[0];
                    this.source.dateDeactivation2 = this.source.dateDeactivation[1];
                } else {
                    this.source.dateDeactivation1 = "";
                    this.source.dateDeactivation2 = "";
                }

                if(this.source.dateActivation !== null && this.source.dateActivation !== "") {
                    this.source.dateActivation1 = this.source.dateActivation[0];
                    this.source.dateActivation2 = this.source.dateActivation[1];
                } else {
                    this.source.dateActivation1 = "";
                    this.source.dateActivation2 = "";
                }

                if(this.source.lastUpdate !== null && this.source.lastUpdate !== "") {
                    this.source.lastUpdate1 = this.source.lastUpdate[0];
                    this.source.lastUpdate2 = this.source.lastUpdate[1];
                } else {
                    this.source.lastUpdate1 = "";
                    this.source.lastUpdate2 = "";
                }

                if(this.source.key === key ) {
                    switch(this.source.sort) {
                        case "":
                            this.source.sort = "asc";
                            break;
                        case "asc":
                            this.source.sort = "desc";
                            break;
                        case "desc":
                            this.source.sort = "";
                            break;
                    }
                }
                else {
                    this.source.key = key;
                    this.source.sort = "asc";
                }
                let formData = new FormData();
                formData.append("sort",this.source.sort);
                formData.append("key",this.source.key);
                formData.append("name",this.source.name);
                formData.append("id",this.source.id);
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
                formData.append("dateCreation1",this.source.dateCreation1);
                formData.append("dateCreation2",this.source.dateCreation2);
                formData.append("dateDeactivation1",this.source.dateDeactivation1);
                formData.append("dateDeactivation2",this.source.dateDeactivation2);
                formData.append("dateActivation1",this.source.dateActivation1);
                formData.append("dateActivation2",this.source.dateActivation2);
                formData.append("lastUpdate1",this.source.lastUpdate1);
                formData.append("lastUpdate2",this.source.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);

                AXIOS.post("/source/getAllNotArchiveSort",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    this.pagination.totalPages = response.data.totalPages;
                    this.pagination.totalElements = response.data.totalElements;
                    this.sourceData = response.data.content;
                })
            },

            updatePage() {
                if(this.source.dateCreation !== null && this.source.dateCreation !== "") {
                    this.source.dateCreation1 = this.source.dateCreation[0];
                    this.source.dateCreation2 = this.source.dateCreation[1];
                } else {
                    this.source.dateCreation1 = "";
                    this.source.dateCreation2 = "";
                }

                if(this.source.dateDeactivation !== null && this.source.dateDeactivation !== "") {
                    this.source.dateDeactivation1 = this.source.dateDeactivation[0];
                    this.source.dateDeactivation2 = this.source.dateDeactivation[1];
                } else {
                    this.source.dateDeactivation1 = "";
                    this.source.dateDeactivation2 = "";
                }

                if(this.source.dateActivation !== null && this.source.dateActivation !== "") {
                    this.source.dateActivation1 = this.source.dateActivation[0];
                    this.source.dateActivation2 = this.source.dateActivation[1];
                } else {
                    this.source.dateActivation1 = "";
                    this.source.dateActivation2 = "";
                }

                if(this.source.lastUpdate !== null && this.source.lastUpdate !== "") {
                    this.source.lastUpdate1 = this.source.lastUpdate[0];
                    this.source.lastUpdate2 = this.source.lastUpdate[1];
                } else {
                    this.source.lastUpdate1 = "";
                    this.source.lastUpdate2 = "";
                }

                let formData = new FormData();
                formData.append("sort",this.source.sort);
                formData.append("key",this.source.key);
                formData.append("name",this.source.name);
                formData.append("id",this.source.id);
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
                formData.append("dateCreation1",this.source.dateCreation1);
                formData.append("dateCreation2",this.source.dateCreation2);
                formData.append("dateDeactivation1",this.source.dateDeactivation1);
                formData.append("dateDeactivation2",this.source.dateDeactivation2);
                formData.append("dateActivation1",this.source.dateActivation1);
                formData.append("dateActivation2",this.source.dateActivation2);
                formData.append("lastUpdate1",this.source.lastUpdate1);
                formData.append("lastUpdate2",this.source.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);
                AXIOS.post("/source/getAllNotArchiveSort", formData)
                    .then(response => {
                        this.sourceData = response.data.content;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            }
        },
        mounted() {
            let formData = new FormData();
            // formData.append("size",this.pagination.pageSize);
            // formData.append("page",0);
            formData.append("sort","");
            formData.append("key","");
            formData.append("name","");
            formData.append("sourceId","");
            formData.append("longName","");
            formData.append("shortName","");
            formData.append("description","");
            formData.append("addDescription","");
            formData.append("scope","");
            formData.append("periodicity","");
            formData.append("renewalPeriod","");
            formData.append("type","");
            formData.append("tags","");
            formData.append("providerLink","");
            formData.append("dataSource","");

            AXIOS.get("source/getAllNotArchive").then(response => {
                this.pagination.totalPages = response.data.totalPages;
                this.pagination.totalElements = response.data.totalElements;
                this.sourceData = response.data.content;
            })
        }
    }
</script>

<style scoped>
    table, td, th {
        border: 1px solid #d7d7d7;
        text-align: center;
    }

    th{
        padding: 10px;
    }

    td{
        padding: 10px;
    }

    table {
        border-collapse: collapse;
        width: 100%;
    }

    th {
        height: 50px;
    }

</style>