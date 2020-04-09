<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Источники
            <el-button @click="deArchiveSomeSource"  style="margin-left: 10px; float: right; margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-upload2"></el-button>
            <el-button class="trt" @click="deleteSomeSource"  style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-delete"></el-button>
            <el-button class="plus" @click="addSource" style="float: right; margin-bottom: 15px; background-color: #1ab394; border-color: #1ab394 "  type="primary" icon="el-icon-plus"></el-button>
            <el-dropdown style="float: right" :hide-on-click="false">
                <el-button style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394; " type="primary" icon="el-icon-s-tools">
                </el-button>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item><el-checkbox checked="checked" @change="hiddenAll">Все</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check" @change="hidden.id = !hidden.id">Номер</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check1" @change="hidden.name = !hidden.name">Поставщик данных</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check2" @change="hidden.longName = !hidden.longName">Полное наименование набора</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check3" @change="hidden.shortName = !hidden.shortName">Краткое наименование набора</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check4" @change="hidden.description = !hidden.description">Описание</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check5" @change="hidden.addDescription = !hidden.addDescription">Дополнительное описание</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check6" @change="hidden.scope = !hidden.scope">Сфера (направление)</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check7" @change="hidden.periodicity = !hidden.periodicity">Периодичность актуализации</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check8" @change="hidden.renewalPeriod = !hidden.renewalPeriod">Срок обновления набора данных</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check9" @change="hidden.type = !hidden.type">Вид набора</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check10" @change="hidden.tags = !hidden.tags">Ключевые слова (теги)</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check11" @change="hidden.providerLink = !hidden.providerLink">Информационная система - источник данных</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check12" @change="hidden.dataSource = !hidden.dataSource">Ссылка на данные на сайте поставщика</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check13" @change="hidden.isArchive = !hidden.isArchive">Архивность</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check14" @change="hidden.dateCreation = !hidden.dateCreation">Дата создания</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check15" @change="hidden.dateDeactivation = !hidden.dateDeactivation">Дата деактивации</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check16" @change="hidden.dateActivation = !hidden.dateActivation">Дата активации</el-checkbox></el-dropdown-item>
                    <el-dropdown-item><el-checkbox checked="checked" id="check17" @change="hidden.lastUpdate = !hidden.lastUpdate">Последнее обновление</el-checkbox></el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>

        </p>
        <div class="horizontal-scroll-wrapper  rectangles">
            <table style="display: block; overflow-x: auto;">
                <tr>
                    <th></th>
                    <th></th>
                    <th v-if="hidden.id" @click="sort('id')">Номер</th>
                    <th v-if="hidden.name" @click="sort('name')">Поставщик данных</th>
                    <th v-if="hidden.longName" @click="sort('long_name')">Полное наименование набора</th>
                    <th v-if="hidden.shortName" @click="sort('short_name')">Краткое наименование набора</th>
                    <th v-if="hidden.description" @click="sort('description')">Описание</th>
                    <th v-if="hidden.addDescription" @click="sort('add_description')">Дополнительное описание</th>
                    <th v-if="hidden.scope" @click="sort('scope')">Сфера (направление)</th>
                    <th v-if="hidden.periodicity" @click="sort('periodicity')">Периодичность актуализации </th>
                    <th v-if="hidden.renewalPeriod" @click="sort('renewal_period')">Срок обновления набора данных</th>
                    <th v-if="hidden.type" @click="sort('type')">Вид набора</th>
                    <th v-if="hidden.tags" @click="sort('tags')">Ключевые слова (теги)</th>
                    <th v-if="hidden.providerLink" @click="sort('provider_link')">Информационная система - источник данных</th>
                    <th v-if="hidden.dataSource" @click="sort('data_source')">Ссылка на данные на сайте поставщика</th>
                    <th v-if="hidden.isArchive" @click="sort('archive')">Архивность</th>
                    <th v-if="hidden.dateCreation" @click="sort('date_creation')">Дата создания</th>
                    <th v-if="hidden.dateDeactivation" @click="sort('date_deactivation')">Дата деактивации</th>
                    <th v-if="hidden.dateActivation" @click="sort('date_activation')">Дата активации</th>
                    <th v-if="hidden.lastUpdate" @click="sort('last_update')">Последнее обновление</th>
                </tr>
                <tr>
                    <td><el-button @click="sort('')"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-search"></el-button></td>
                    <td></td>
                    <td v-if="hidden.id"><el-input placeholder="Please input" v-model="source.id"></el-input></td>
                    <td v-if="hidden.name"><el-input placeholder="Please input" v-model="source.name"></el-input></td>
                    <td v-if="hidden.longName"><el-input placeholder="Please input" v-model="source.longName"></el-input></td>
                    <td v-if="hidden.shortName"><el-input placeholder="Please input" v-model="source.shortName"></el-input></td>
                    <td v-if="hidden.description"><el-input placeholder="Please input" v-model="source.description"></el-input></td>
                    <td v-if="hidden.addDescription"><el-input placeholder="Please input" v-model="source.addDescription"></el-input></td>
                    <td v-if="hidden.scope"><el-input placeholder="Please input" v-model="source.scope"></el-input></td>
                    <td v-if="hidden.periodicity"><el-input placeholder="Please input" v-model="source.periodicity"></el-input></td>
                    <td v-if="hidden.renewalPeriod"><el-input placeholder="Please input" v-model="source.renewalPeriod"></el-input></td>
                    <td v-if="hidden.type"><el-input placeholder="Please input" v-model="source.type"></el-input></td>
                    <td v-if="hidden.tags"><el-input placeholder="Please input" v-model="source.tags"></el-input></td>
                    <td v-if="hidden.providerLink"><el-input placeholder="Please input" v-model="source.providerLink"></el-input></td>
                    <td v-if="hidden.dataSource"><el-input placeholder="Please input" v-model="source.dataSource"></el-input></td>
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
                                v-model="source.dateCreation"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
                    <td v-if="hidden.dateDeactivation"> <div class="block">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="source.dateDeactivation"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
                    <td v-if="hidden.dateActivation"> <div class="block">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="source.dateActivation"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
                    <td v-if="hidden.lastUpdate"> <div class="block">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="source.lastUpdate"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
                </tr>
                <tbody v-for="source in sourceData">
                <tr >
                    <td>
                        <span v-if="source.isArchive">
                            <el-button @click="deArchiveOneSource(source.id)"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-upload2"></el-button>
                        </span>
                        <span v-else>
                            <el-button @click="deleteOneSource(source.id)"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-delete"></el-button>
                        </span>
                        <br>
                        <el-button @click="updateSource(source.id)" style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394" type="primary" size="mini" icon="el-icon-edit"></el-button>
                        <br>
                        <el-button @click="sourceView(source.id)" style="background-color: #1ab394; border-color: #1ab394" type="primary" size="mini" icon="el-icon-view"></el-button>
                    </td>
                    <td><el-checkbox @change="check(source.id)"></el-checkbox></td>
                    <td v-if="hidden.id">{{source.id}}</td>
                    <td v-if="hidden.name">{{source.name}}</td>
                    <td v-if="hidden.longName">{{source.longName}}</td>
                    <td v-if="hidden.shortName">{{source.shortName}}</td>
                    <td v-if="hidden.description">{{source.description}}</td>
                    <td v-if="hidden.addDescription">{{source.addDescription}}</td>
                    <td v-if="hidden.scope">{{source.scope}}</td>
                    <td v-if="hidden.periodicity">{{source.periodicity}}</td>
                    <td v-if="hidden.renewalPeriod">{{source.renewalPeriod}}</td>
                    <td v-if="hidden.type">{{source.type}}</td>
                    <td v-if="hidden.tags">{{source.tags}}</td>
                    <td v-if="hidden.providerLink">{{source.providerLink}}</td>
                    <td v-if="hidden.dataSource">{{source.dataSource}}</td>
                    <td v-if="hidden.isArchive">{{source.isArchive ? "Да" : "Нет"}}</td>
                    <td v-if="hidden.dateCreation">{{source.dateCreation}}</td>
                    <td v-if="hidden.dateDeactivation">{{source.dateDeactivation}}</td>
                    <td v-if="hidden.dateActivation">{{source.dateActivation}}</td>
                    <td v-if="hidden.lastUpdate">{{source.lastUpdate}}</td>
                </tr>
                </tbody>

            </table>
        </div>
        <el-backtop target=".trt"></el-backtop>
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
        name: "sourceTable",
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
                checked: true,
                url:"source/getAll?",
                sourceData: "",
                pagination:{
                    pageSize: 10,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                },
                hidden:{
                    id:true,
                    name:true,
                    longName:true,
                    shortName:true,
                    description:true,
                    addDescription:true,
                    scope:true,
                    periodicity:true,
                    renewalPeriod:true,
                    type:true,
                    tags:true,
                    providerLink:true,
                    dataSource:true,
                    isArchive:true,
                    dateCreation:true,
                    dateDeactivation:true,
                    dateActivation:true,
                    lastUpdate:true,
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
                document.getElementById("check10").click();
                document.getElementById("check11").click();
                document.getElementById("check12").click();
                document.getElementById("check13").click();
                document.getElementById("check14").click();
                document.getElementById("check15").click();
                document.getElementById("check16").click();
                document.getElementById("check17").click();
            },

            check(id) {
                let key = this.source.check.indexOf(id);
                if(key !== -1){
                    this.source.check.splice(key,1);
                } else {
                    this.source.check.push(id);
                }
            },

            deArchiveSource(id){
                AXIOS.get("source/deArchive/" + id).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Источник был активирован','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Источник не был активирован','error');
                    }
                });
            },

            deArchiveOneSource(id){
                this.$confirm('Разархивировать все связанные с этим источником шаблоны', 'Разархивировать', {
                    confirmButtonText: 'OK',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    this.deArchiveSource(id);
                    AXIOS.get("pattern/archivePatterns/" + id);
                    this.$message({
                        type: 'success',
                        message: 'Источник разархивирован вместе с шаблонами'
                    });
                }).catch(() => {
                    this.deArchiveSource(id);
                    this.$message({
                        type: 'success',
                        message: 'Источник разархивирован без шаблонов'
                    });
                });
            },

            deArchiveSomeSource(){
                this.$confirm('Архивировать все связанные с этим источником шаблоны', 'Архвировать', {
                    confirmButtonText: 'OK',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    if(this.source.check.length !== 0){
                        for(let i = 0; i < this.source.check.length; i++){
                            this.deArchiveSource(this.source.check[i]);
                        }
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Выберите источники которые хотите сделать активным','error');
                    }
                    // AXIOS.get("pattern/archivePatterns/" + i);
                    this.$message({
                        type: 'warning',
                        message: 'Источник архивирован вместе с шаблонами'
                    });

                }).catch(() => {
                    if(this.source.check.length !== 0){
                        for(let i = 0; i < this.source.check.length; i++){
                            this.deArchiveSource(this.source.check[i]);
                        }
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Выберите источники которые хотите сделать активным','error');
                    }
                    this.$message({
                        type: 'success',
                        message: 'Источник архивирован без шаблонов'
                    });
                });
            },

            deleteSource(id) {
                AXIOS.get("source/archive/" + id).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Источник был архивирован','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Источник не был архивирован','error');
                    }
                });
            },

            deleteOneSource(id) {
                this.$confirm('Архивировать все связанные с этим источником шаблоны', 'Архвировать', {
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

            deleteSomeSource() {
                this.$confirm('Архивировать все связанные с этим источником шаблоны', 'Архвировать', {
                    confirmButtonText: 'OK',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    console.log("yes");
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
                    console.log("no");
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
                formData.append("isArchive",this.value);
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
                AXIOS.post("/source/getAllSort", formData)
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
                formData.append("isArchive",this.value);
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
                AXIOS.post("/source/getAllSort", formData)
                    .then(response => {
                        this.sourceData = response.data.content;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            },

            addSource() {
                router.push('create');
            },

            updateSource(id) {
                router.push('update/'+ id);
            },

            sourceView(id) {
                router.push('view/'+ id);
            },

            notify(title,message,type) {
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
                formData.append("isArchive",this.value);
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

                AXIOS.post("/source/getAllSort",
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
                formData.append("isArchive",this.value);
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
                AXIOS.post("/source/getAllSort", formData)
                    .then(response => {
                        this.sourceData = response.data.content;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            }
        },
        mounted() {
            AXIOS.get("source/getAll").then(response => {
                    this.pagination.totalPages = response.data.totalPages;
                    this.pagination.totalElements = response.data.totalElements;
                    this.sourceData = response.data.content;
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