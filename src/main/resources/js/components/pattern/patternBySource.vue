<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p v-if="!createPattern" style="font-size: 20px">Шаблоны
<!--            <el-button v-if="access" class="trt" @click="deleteSomePattern"  style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-delete"></el-button>-->
<!--            <el-button v-if="access" @click="deArchiveSomePattern"  style="float: right; margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-upload2"></el-button>-->
<!--            <el-button v-if="access" class="plus" @click="addPattern" style="float: right; margin-bottom: 15px; background-color: #1ab394; border-color: #1ab394 "  type="primary" icon="el-icon-plus"></el-button>-->
            <el-button class="trt" @click="deleteSomePattern"  style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-delete"></el-button>
            <el-button @click="deArchiveSomePattern"  style="float: right; margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-upload2"></el-button>
            <el-button class="plus" @click="addPattern" style="float: right; margin-bottom: 15px; background-color: #1ab394; border-color: #1ab394 "  type="primary" icon="el-icon-plus"></el-button>

            <el-dropdown style="float: right" :hide-on-click="false">
                <el-button
                        style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394; "
                        type="primary"
                        icon="el-icon-s-tools">
                </el-button>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>
                        <el-checkbox checked="checked" @change="hiddenAll">
                            Все
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="patternCheck" @change="hidden.id = !hidden.id">
                            Номер
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="patternCheck1" @change="hidden.name = !hidden.name">
                            Навание
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="patternCheck2" @change="hidden.description = !hidden.description">
                            Описание
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="patternCheck3" @change="hidden.direction = !hidden.direction">
                            Направление
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="patternCheck4" @change="hidden.management = !hidden.management">
                            Ответсвенный за ведение
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="patternCheck5" @change="hidden.isArchive = !hidden.isArchive">
                            Архивность
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="patternCheck6" @change="hidden.dateCreation = !hidden.dateCreation">
                            Дата создания
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="patternCheck7" @change="hidden.dateDeactivation = !hidden.dateDeactivation">
                            Дата деактивации
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="patternCheck8" @change="hidden.dateActivation = !hidden.dateActivation">
                            Дата активации
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="patternCheck9" @change="hidden.lastUpdate = !hidden.lastUpdate">
                            Последнее обновление
                        </el-checkbox>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </p>
        <div v-if="!createPattern" class="horizontal-scroll-wrapper  rectangles">
            <table style="display: block; overflow-x: auto; ">
                <tr>
                    <th></th>
                    <th><el-checkbox ></el-checkbox></th>
                    <th v-if="hidden.id" @click="sort('id')">Номер</th>
                    <th v-if="hidden.name" @click="sort('name')">Навание</th>
                    <th v-if="hidden.description" @click="sort('description')">Описание</th>
                    <th v-if="hidden.direction" @click="sort('direction')">Направление </th>
                    <th v-if="hidden.management" @click="sort('management')">Ответсвенный за ведение </th>
                    <th v-if="hidden.isArchive" @click="sort('archive')">Архивность</th>
                    <th v-if="hidden.dateCreation" @click="sort('date_creation')">Дата создания</th>
                    <th v-if="hidden.dateDeactivation" @click="sort('date_deactivation')">Дата деактивации</th>
                    <th v-if="hidden.dateActivation" @click="sort('date_activation')">Дата активации</th>
                    <th v-if="hidden.lastUpdate" @click="sort('last_update')">Последнее обновление</th>
                </tr>
                <tr>
                    <td>
                        <el-button
                                @click="sort('')"
                                style=" background-color: #1ab394; border-color: #1ab394 "
                                type="primary"
                                size="mini"
                                icon="el-icon-search"/>
                    </td>
                    <td></td>
                    <td v-if="hidden.id">
                        <el-input
                                placeholder="Please input"
                                v-model="pattern.id"/>
                    </td>
                    <td v-if="hidden.name">
                        <el-input
                                placeholder="Please input"
                                v-model="pattern.name"/>
                    </td>
                    <td v-if="hidden.description">
                        <el-input
                                placeholder="Please input"
                                v-model="pattern.description"/>
                    </td>
                    <td v-if="hidden.direction">
                        <el-input
                                placeholder="Please input"
                                v-model="pattern.direction"/>
                    </td>
                    <td v-if="hidden.management">
                        <el-input
                                placeholder="Please input"
                                v-model="pattern.management"/>
                    </td>
                    <td v-if="hidden.isArchive">
                        <el-select v-model="value" placeholder="Select">
                            <el-option
                                    v-for="item in options"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </td>
                    <td v-if="hidden.dateCreation">
                        <div class="block">
                            <el-date-picker
                                    value-format="yyyy-MM-dd"
                                    v-model="pattern.dateCreation"
                                    type="daterange"
                                    range-separator="To"
                                    start-placeholder="Start date"
                                    end-placeholder="End date">
                            </el-date-picker>
                        </div>
                    </td>
                    <td v-if="hidden.dateDeactivation">
                        <div class="block">
                            <el-date-picker
                                    value-format="yyyy-MM-dd"
                                    v-model="pattern.dateDeactivation"
                                    type="daterange"
                                    range-separator="To"
                                    start-placeholder="Start date"
                                    end-placeholder="End date">
                            </el-date-picker>
                        </div>
                    </td>
                    <td v-if="hidden.dateActivation">
                        <div class="block">
                            <el-date-picker
                                    value-format="yyyy-MM-dd"
                                    v-model="pattern.dateActivation"
                                    type="daterange"
                                    range-separator="To"
                                    start-placeholder="Start date"
                                    end-placeholder="End date">
                            </el-date-picker>
                        </div>
                    </td>
                    <td v-if="hidden.lastUpdate">
                        <div class="block">
                            <el-date-picker
                                    value-format="yyyy-MM-dd"
                                    v-model="pattern.lastUpdate"
                                    type="daterange"
                                    range-separator="To"
                                    start-placeholder="Start date"
                                    end-placeholder="End date">
                            </el-date-picker>
                        </div>
                    </td>
                </tr>
                <tbody v-for="pattern in patternData">
                <tr>
                    <td>
                        <el-button
                                @click="showCard(pattern.id)"
                                style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                type="primary"
                                size="mini"
                                icon="el-icon-view"/>
                        <span v-if="pattern.isArchive">
<!--                             <el-button-->
<!--                                     v-if="access"-->
<!--                                     @click="deArchiveOnePattern(pattern.id)"-->
<!--                                     style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "-->
<!--                                     type="primary"-->
<!--                                     size="mini"-->
<!--                                     icon="el-icon-upload2"/>-->
                             <el-button
                                     @click="deArchiveOnePattern(pattern.id)"
                                     style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                     type="primary"
                                     size="mini"
                                     icon="el-icon-upload2"/>
                         </span>
                        <span v-else>
<!--                            <el-button-->
<!--                                    v-if="access"-->
<!--                                    @click="deleteOnePattern(pattern.id)"-->
<!--                                    style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "-->
<!--                                    type="primary"-->
<!--                                    size="mini"-->
<!--                                    icon="el-icon-delete"/>-->
                             <el-button
                                     @click="deleteOnePattern(pattern.id)"
                                     style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                     type="primary"
                                     size="mini"
                                     icon="el-icon-delete"/>
                         </span>
                    </td>
                    <td> <el-checkbox @change="check(pattern.id)"></el-checkbox></td>
                    <td v-if="hidden.id">{{pattern.id}}</td>
                    <td v-if="hidden.name">{{pattern.name}}</td>
                    <td v-if="hidden.description">{{pattern.description}}</td>
                    <td v-if="hidden.direction">{{pattern.direction}}</td>
                    <td v-if="hidden.management">{{pattern.management}}</td>
                    <td v-if="hidden.isArchive">{{pattern.isArchive ? "Да" : "Нет"}}</td>
                    <td v-if="hidden.dateCreation">{{pattern.dateCreation}}</td>
                    <td v-if="hidden.dateDeactivation">{{pattern.dateDeactivation}}</td>
                    <td v-if="hidden.dateActivation">{{pattern.dateActivation}}</td>
                    <td v-if="hidden.lastUpdate">{{pattern.lastUpdate}}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <span v-else-if="createPattern">
            <pattern-create :source-id="this.sourceId"/>
            <br>
            <el-button
                    @click="backAdd"
                    style="background-color: #1ab394; border-color: #1ab394; color: white;">
                Назад
            </el-button>
        </span>
        <my-pagination v-if="!createPattern"
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
    import PatternCreate from "./patternCreate.vue";
    import {getToken} from "../../modules/auth";
    export default {
        name: "patternBySource",
        props:['sourceId'],
        // props:['sourceId',"access"],
        components: {PatternCreate, MyPagination},
        data() {
            return {
                createPattern:false,
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
                url: "source/getAll?",
                patternData: "",
                pagination: {
                    pageSize: 10,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                },

                hidden:{
                    id: true,
                    name: true,
                    description: true,
                    direction: true,
                    management: true,
                    isArchive: true,
                    dateCreation: true,
                    dateDeactivation: true,
                    dateActivation: true,
                    lastUpdate: true,
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
                }
            }
        },

        methods: {
            backAdd(){
                this.createPattern = false;
                AXIOS.get("pattern/getAll/" + this.sourceId).then(response => {
                    this.pagination.totalPages = response.data.totalPages;
                    this.pagination.totalElements = response.data.totalElements;
                    this.patternData = response.data.content;
                })
            },

            addPattern(){
                this.createPattern = true;
            },

            hiddenAll(){
                document.getElementById("patternCheck").click();
                document.getElementById("patternCheck1").click();
                document.getElementById("patternCheck2").click();
                document.getElementById("patternCheck3").click();
                document.getElementById("patternCheck4").click();
                document.getElementById("patternCheck5").click();
                document.getElementById("patternCheck6").click();
                document.getElementById("patternCheck7").click();
                document.getElementById("patternCheck8").click();
                document.getElementById("patternCheck9").click();
            },

            showCard(patternId){
                router.push('/pattern/card/'+ patternId);
            },

            check(id) {
                let key = this.pattern.check.indexOf(id);
                if(key !== -1){
                    this.pattern.check.splice(key,1);
                } else {
                    this.pattern.check.push(id);
                }
            },

            deArchiveSomePattern(){
                if(this.pattern.check.length !== 0){
                    for(let i = 0; i < this.pattern.check.length; i++){
                        this.deArchivePattern(this.pattern.check[i]);
                    }
                    this.updatePage();
                } else {
                    this.notify('Ошибка','Выберите источники которые хотите сделать активным','error');
                }
            },

            deleteSomePattern() {
                if(this.pattern.check.length !== 0){
                    for(let i = 0; i < this.pattern.check.length; i++){
                        this.deletePattern(this.pattern.check[i]);
                        AXIOS.get("tableCreator/archivePatterns/" + i)
                    }
                    this.updatePage();
                } else {
                    this.notify('Ошибка','Выберите источники которые хотите архивировать','error');
                }
            },

            notify(title,message,type) {
                this.$notify({
                    title: title,
                    message: message,
                    type: type
                });
            },

            deletePattern(id) {
                AXIOS.get("pattern/archive/" + id ).then(response => {
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
                AXIOS.get("tableCreator/archivePatterns/" + id)
            },

            deArchivePattern(id){
                AXIOS.get("pattern/deArchive/" + id ).then(response => {
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
                formData.append("sourceId",this.sourceId);
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

                AXIOS.post("/pattern/getAllSort/" + this.sourceId,
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

                AXIOS.post("/pattern/getAllSort/" + this.sourceId, formData)
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

                AXIOS.post("/pattern/getAllSort/" + this.sourceId, formData)
                    .then(response => {
                        this.patternData = response.data.content;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            },

            updatePage() {
                if (this.pattern.dateCreation !== null && this.pattern.dateCreation !== "") {
                    this.pattern.dateCreation1 = this.pattern.dateCreation[0];
                    this.pattern.dateCreation2 = this.pattern.dateCreation[1];
                } else {
                    this.pattern.dateCreation1 = "";
                    this.pattern.dateCreation2 = "";
                }

                if (this.pattern.dateDeactivation !== null && this.pattern.dateDeactivation !== "") {
                    this.pattern.dateDeactivation1 = this.pattern.dateDeactivation[0];
                    this.pattern.dateDeactivation2 = this.pattern.dateDeactivation[1];
                } else {
                    this.pattern.dateDeactivation1 = "";
                    this.pattern.dateDeactivation2 = "";
                }

                if (this.pattern.dateActivation !== null && this.pattern.dateActivation !== "") {
                    this.pattern.dateActivation1 = this.pattern.dateActivation[0];
                    this.pattern.dateActivation2 = this.pattern.dateActivation[1];
                } else {
                    this.pattern.dateActivation1 = "";
                    this.pattern.dateActivation2 = "";
                }

                if (this.pattern.lastUpdate !== null && this.pattern.lastUpdate !== "") {
                    this.pattern.lastUpdate1 = this.pattern.lastUpdate[0];
                    this.pattern.lastUpdate2 = this.pattern.lastUpdate[1];
                } else {
                    this.pattern.lastUpdate1 = "";
                    this.pattern.lastUpdate2 = "";
                }

                let formData = new FormData();
                formData.append("isArchive",this.value);
                formData.append("sort", this.pattern.sort);
                formData.append("key", this.pattern.key);
                formData.append("id", this.pattern.id);
                formData.append("name", this.pattern.name);
                formData.append("description", this.pattern.description);
                formData.append("management", this.pattern.management);
                formData.append("periodicity", this.pattern.periodicity);
                formData.append("dateCreation1", this.pattern.dateCreation1);
                formData.append("dateCreation2", this.pattern.dateCreation2);
                formData.append("dateDeactivation1", this.pattern.dateDeactivation1);
                formData.append("dateDeactivation2", this.pattern.dateDeactivation2);
                formData.append("dateActivation1", this.pattern.dateActivation1);
                formData.append("dateActivation2", this.pattern.dateActivation2);
                formData.append("lastUpdate1", this.pattern.lastUpdate1);
                formData.append("lastUpdate2", this.pattern.lastUpdate2);
                formData.append("size", this.pagination.pageSize);
                formData.append("page", this.pagination.currentPage - 1);

                AXIOS.post("/pattern/getAllSort/" + this.sourceId,
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
            }
        },

        mounted() {
            AXIOS.get("pattern/getAll/" + this.sourceId).then(response => {
                this.pagination.totalPages = response.data.totalPages;
                this.pagination.totalElements = response.data.totalElements;
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

    .el-dropdown {
        vertical-align: top;
    }
    .el-dropdown + .el-dropdown {
        margin-left: 15px;
    }
    .el-icon-arrow-down {
        font-size: 12px;
    }
</style>