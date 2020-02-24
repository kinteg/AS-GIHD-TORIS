<template>
    <div>
        <el-collapse>
            <el-collapse-item title="Создать источник">
                <h2>Создание источника</h2>
                <create-source/>
            </el-collapse-item>/
        </el-collapse>
        <h2>Поиск источника</h2>
        <search-source/>
        <el-form>
            <el-form-item>
                <el-button style="margin-top: 10px" type="primary" @click="submit">Найти</el-button>
            </el-form-item>
        </el-form>
        <h3>{{data.name}}</h3>
        <div v-for="map in patternMap">
            <show-source :table="map.data" :pattern="map.pattern"/>
        </div>
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
                pattern:[],
                tables:[],
                patternMap:[]
            }
        },
        methods:{

            submit(){
                let sourceName = document.getElementById('sourceList').value;
                AXIOS.get('/source/'+ sourceName,
                ).then(response=>{
                    this.data = response.data;
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
                        AXIOS.get('/source/getTable/'+ this.pattern[i].id,
                        ).then(response1=>{
                            let kek = response1.data.content;
                            this.patternMap.push({pattern:this.pattern[i],data:kek});
                            console.log(this.patternMap);
                        }).catch(error=>{
                            console.log("ERROR"+error);
                        });
                    }
                }).catch(error=>{
                    console.log("ERROR"+error);
                });
            },
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