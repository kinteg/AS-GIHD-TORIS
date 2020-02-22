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

        <el-collapse>
            <el-collapse-item title="Создать шаблон">
                <el-collapse>
                    <el-collapse-item title="Создать шаблон">
                        asdasdsa
                    </el-collapse-item>
                    <el-collapse-item title="Создать шаблон">
                        asdasdsa
                    </el-collapse-item></el-collapse>
            </el-collapse-item>
        </el-collapse>
    </div>
</template>

<script>
    import SearchSource from "../components/SearchSource.vue";
    import CreateSource from "../components/CreateSource.vue";
    import {AXIOS} from "../AXIOS/http-common";
    export default {
        name: "Source",
        components: {CreateSource, SearchSource},
        data(){
            return{
                data:'',
                pattern:'',
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
                    console.log(this.pattern);
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