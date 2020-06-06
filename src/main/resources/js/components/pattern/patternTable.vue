<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/pattern/show' }">Все шаблоны</el-breadcrumb-item>
        </el-breadcrumb>
        <p style="font-size: 20px">Шаблоны
            <el-button class="trt" @click="deleteSomePattern"  style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-delete"></el-button>
            <el-button @click="deArchiveSomePattern"  style="float: right; margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-upload2"></el-button>
            <el-dropdown style="float: right" :hide-on-click="false">
                <el-button style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394; " type="primary" icon="el-icon-s-tools">
                </el-button>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>
                        <el-checkbox checked="checked" @change="hiddenAll">
                            Все
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="check" @change="hidden.id = !hidden.id">
                            Номер
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="check1" @change="hidden.name = !hidden.name">
                            Навание
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="check2" @change="hidden.description = !hidden.description">
                            Описание
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="check3" @change="hidden.direction = !hidden.direction">
                            Направление
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="check4" @change="hidden.management = !hidden.management">
                            Ответсвенный за ведение
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="check5" @change="hidden.isArchive = !hidden.isArchive">
                            Архивность
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="check6" @change="hidden.dateCreation = !hidden.dateCreation">
                            Дата создания
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="check7" @change="hidden.dateDeactivation = !hidden.dateDeactivation">
                            Дата деактивации
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="check8" @change="hidden.dateActivation = !hidden.dateActivation">
                            Дата активации
                        </el-checkbox>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <el-checkbox checked="checked" id="check9" @change="hidden.lastUpdate = !hidden.lastUpdate">
                            Последнее обновление
                        </el-checkbox>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </p>
        <div v-if="patternData.length !== 0" class="horizontal-scroll-wrapper  rectangles">
            <table style="display: block; overflow-x: auto; ">
                <tr>
                    <th></th>
                    <th></th>
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
                    <td v-if="hidden.id"><el-input placeholder="Please input" v-model="pattern.id"></el-input></td>
                    <td v-if="hidden.name"><el-input placeholder="Please input" v-model="pattern.name"></el-input></td>
                    <td v-if="hidden.description"><el-input placeholder="Please input" v-model="pattern.description"></el-input></td>
                    <td v-if="hidden.direction"><el-input placeholder="Please input" v-model="pattern.direction"></el-input></td>
                    <td v-if="hidden.management"><el-input placeholder="Please input" v-model="pattern.management"></el-input></td>
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
                    <td v-if="hidden.dateCreation"> <div class="block">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="pattern.dateCreation"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
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
                        <div v-if="!pattern.sourceArchive">
                            <span v-if="pattern.isArchive">
                                <el-button
                                        @click="deArchiveOnePattern(pattern.id, pattern.sourceId)"
                                        style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                        type="primary"
                                        size="mini"
                                        icon="el-icon-upload2"/>
                            </span>
                            <span v-else>
                                <el-button
                                        @click="deleteOnePattern(pattern.id, pattern.sourceId)"
                                        style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                        type="primary"
                                        size="mini"
                                        icon="el-icon-delete"/>
                            </span>
                        </div>
                    </td>
                    <td> <el-checkbox @change="check(pattern.id)"></el-checkbox></td>
                    <td v-if="hidden.id">
                        {{pattern.id}}
                    </td>
                    <td v-if="hidden.name && pattern.name.length < 40">
                        {{pattern.name}}
                    </td>
                    <td v-else-if="hidden.name && pattern.name.length >= 40">
                        {{pattern.name.substr(0, 40)}}
                        <router-link :to="'/pattern/card/' + pattern.id">...</router-link>
                    </td>
                    <td v-if="hidden.description && pattern.description.length < 40">
                        {{pattern.description}}
                    </td>
                    <td v-else-if="hidden.description && pattern.description.length >= 40">
                        {{pattern.description.substr(0, 40)}}
                        <router-link :to="'/pattern/card/' + pattern.id">...</router-link>
                    </td>
                    <td v-if="hidden.direction && pattern.direction.length < 40">
                        {{pattern.direction}}
                    </td>
                    <td v-else-if="hidden.direction && pattern.direction.length >= 40">
                        {{pattern.direction.substr(0, 40)}}
                        <router-link :to="'/pattern/card/' + pattern.id">...</router-link>
                    </td>
                    <td v-if="hidden.management && pattern.management.length < 40">
                        {{pattern.management}}
                    </td>
                    <td v-else-if="hidden.management && pattern.management.length >= 40">
                        {{pattern.management.substr(0, 40)}}
                        <router-link :to="'/pattern/card/' + pattern.id">...</router-link>
                    </td>
                    <td v-if="hidden.isArchive">
                        {{pattern.isArchive ? "Да" : "Нет"}}
                    </td>
                    <td v-if="hidden.dateCreation">
                        {{pattern.dateCreation}}
                    </td>
                    <td v-if="hidden.dateDeactivation">
                        {{pattern.dateDeactivation}}
                    </td>
                    <td v-if="hidden.dateActivation">
                        {{pattern.dateActivation}}
                    </td>
                    <td v-if="hidden.lastUpdate">
                        {{pattern.lastUpdate}}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div v-else>
            <p style="font-size: 20px">Данных нет</p>
        </div>
        <my-pagination
                v-if="patternData.length !== 0"
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
    import {getToken} from "../../modules/auth";
    export default {
        name: "patternTable",
        components: {MyPagination},
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
            hiddenAll(){
                document.getElementById("check").click();
                document.getElementById("check1").click();
                document.getElementById("check2").click();
                document.getElementById("check3").click();
                document.getElementById("check4").click();
                document.getElementById("check5").click();
                document.getElementById("check6").click();
                document.getElementById("check7").click();
                document.getElementById("check8").click();
                document.getElementById("check9").click();
            },

            showCard(patternId){
                router.push('card/'+ patternId);
            },

            check(id) {
                let key = this.pattern.check.indexOf(id);
                if(key !== -1){
                    this.pattern.check.splice(key,1);
                } else {
                    this.pattern.check.push(id);
                }
            },

            notify(title,message,type) {
                this.$notify({
                    title: title,
                    message: message,
                    type: type
                });
            },

            deleteSomePattern() {
                if(this.pattern.check.length !== 0){
                    for(let i = 0; i < this.pattern.check.length; i++){
                        // AXIOS.get("user/isChangeSource/" + getToken() + "/" + i).then(response=> {
                        //     if (response.data) {
                                this.deletePattern(this.pattern.check[i]);
                                AXIOS.get("tableCreator/archivePatterns/" + i )
                            // } else {
                            //     this.notify('Ошибка', 'У вас недостаточно прав', 'error');
                            // }
                        // });
                    }
                    this.updatePage();
                } else {
                    this.notify('Ошибка','Выберите источники которые хотите архивировать','error');
                }
            },

            deletePattern(id) {
                AXIOS.get("pattern/archive/" + id).then(response => {
                // AXIOS.get("pattern/archive/" + id  + "/" + getToken()).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Шаблон был архивирован','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Шаблон не был архивирован','error');
                    }
                });
            },

            deleteOnePattern(id, sourceId) {
                // AXIOS.get("user/isChangeSource/" + getToken() + "/" + sourceId).then(response=> {
                //     if (response.data) {
                        this.deletePattern(id);
                        AXIOS.get("tableCreator/archivePatterns/" + id );
                        // AXIOS.get("tableCreator/archivePatterns/" + id + "/" + getToken())
                //     } else {
                //         this.notify('Ошибка', 'У вас недостаточно прав', 'error');
                //     }
                // });
            },

            deArchiveSomePattern(){
                if(this.pattern.check.length !== 0){
                    for(let i = 0; i < this.pattern.check.length; i++){
                        // AXIOS.get("user/isChangeSource/" + getToken() + "/" + i).then(response=> {
                        //     if (response.data) {
                                this.deArchivePattern(this.pattern.check[i]);
                        //     } else {
                        //         this.notify('Ошибка', 'У вас недостаточно прав', 'error');
                        //     }
                        // });
                    }
                    this.updatePage();
                } else {
                    this.notify('Ошибка','Выберите источники которые хотите сделать активным','error');
                }
            },

            deArchivePattern(id){
                // AXIOS.get("pattern/deArchive/" + id + "/" + getToken()).then(response => {
                AXIOS.get("pattern/deArchive/" + id ).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Шаблон был активирован','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Шаблон не был активирован','error');
                    }
                });
            },

            deArchiveOnePattern(id, sourceId){
                // AXIOS.get("user/isChangeSource/" + getToken() + "/" + sourceId).then(response=> {
                //     if (response.data) {
                        this.deArchivePattern(id, sourceId);
                //     } else {
                //         this.notify('Ошибка', 'У вас недостаточно прав', 'error');
                //     }
                // });
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
            }
        },
        mounted() {
            AXIOS.get("pattern/getAll").then(response => {
                this.pagination.totalPages = response.data.totalPages;
                this.pagination.totalElements = response.data.totalElements;
                let pattern = response.data.content;
                for (let i = 0; i < pattern.length; i++) {
                    AXIOS.get("pattern/" + pattern[i].id).then(response => {
                        let sourceId = response.data.sourceId;
                        let man = response.data.management;
                        AXIOS.get("source/isArchive/" + sourceId).then(response => {
                            pattern[i].sourceArchive = response.data;
                            pattern[i].management = response.data;
                            pattern[i].management = man;
                        });
                    });
                }
                this.patternData = pattern;
            });
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