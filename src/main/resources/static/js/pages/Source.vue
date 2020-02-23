<template>
    <div>
        <el-form status-icon  ref="ruleForm" label-width="120px" class="demo-ruleForm">
            <el-form-item >
                <el-collapse>
                    <el-collapse-item title="Создать шаблон">
                        <h2>Создание источника</h2>
                        <create-source/>
                    </el-collapse-item>
                </el-collapse>
            </el-form-item>
            <el-form-item>
                <h2>Поиск источника</h2>
                <search-source/>
                <el-button style="margin-top: 10px" type="primary" @click="submit">Найти</el-button>

            </el-form-item>
        </el-form>
        <h3>{{data.name}}</h3>

        <show-source :data="pattern"/>

    </div>
</template>

<script>
    import SearchSource from "../components/SearchSource.vue";
    import CreateSource from "../components/CreateSource.vue";
    import {AXIOS} from "../AXIOS/http-common";
    import ShowSource from "../components/ShowSource.vue";
    export default {
        name: "Source",
        components: {ShowSource, CreateSource, SearchSource},
        data(){
            return{
                data:'',
                pattern:'',
                table:'',
            }
        },
        methods:{

            submit(){
                let sourceName = document.getElementById('sourceList').value;
                // console.log(sourceName);
                AXIOS.get('/source/'+ sourceName,
                ).then(response=>{
                    this.data = response.data;
                    // console.log(this.data);
                    this.getPattern(this.data.id)
                }).catch(error=>{
                    console.log("ERROR"+error);
                });
            },

            getPattern(id){
                AXIOS.get('/pattern/'+ id,
                ).then(response=>{
                    this.pattern = response.data;
                    for(let i = 0; i<this.pattern.length; i++){
                        this.getTable(this.pattern[i].id);
                    }
                }).catch(error=>{
                    console.log("ERROR"+error);
                });
            },

            getTable(id){
                AXIOS.get('/source/getTable/'+ id,
                ).then(response=>{
                    this.table = response.data;
                    console.log(this.table);
                }).catch(error=>{
                    console.log("ERROR"+error);
                });
            }

        }
    }
</script>

<style scoped>
    .el-container {
        padding: 20px;
        margin: auto;
        max-width: 950px;
        position: relative;
        display: block;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }
    div {
        font-family: 'PdfIntextCondPro-Bold';
        font-size: 100% !important;
    }
</style>