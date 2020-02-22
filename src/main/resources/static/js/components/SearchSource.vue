<template>
    <div>
        <el-autocomplete
                id="sourceList"
                class="inline-input"
                v-model="state2"
                :fetch-suggestions="querySearch"
                placeholder="Введите название источника"
                :trigger-on-focus="false"
                @select="handleSelect">
        </el-autocomplete>
    </div>
</template>
<script>
    import {AXIOS} from "../AXIOS/http-common";

    export default {
        name: "SearchSource",
        data() {
            return {
                links: [],
                state1: '',
                state2: '',
                source: []
            };
        },
        methods: {
            querySearch(queryString, cb) {

                let source = this.source;
                let results = queryString ? source.filter(this.createFilter(queryString)) : source;
                cb(results);
            },
            createFilter(queryString) {
                return (link) => {
                    return (link.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },

            handleSelect(item) {
                console.log(item);
            }
        },

        mounted() {
            let source =[];
            AXIOS.get('/source/getAll',
            ).then(response=>{
                source = response.data;
                for(let i = 0;i<source.length; i++) {
                    this.source.push({"value":source[i].name});
                }
            }).catch(error=>{
                console.log("ERROR"+error);
            });
        },
    }
</script>