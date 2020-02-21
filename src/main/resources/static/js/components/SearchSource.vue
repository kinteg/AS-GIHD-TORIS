<template>
    <div>
        <div class="sub-title">Введите название источника</div>
        <el-autocomplete
                class="inline-input"
                v-model="state2"
                :fetch-suggestions="querySearch"
                placeholder="Введите название источника"
                :trigger-on-focus="false"
                @select="handleSelect">
        </el-autocomplete>
        <el-button  type="primary">Найти</el-button>
    </div>
</template>
<script>
    export default {
        name: "SearchSource",
        data() {
            return {
                links: [],
                state1: '',
                state2: ''
            };
        },
        methods: {
            querySearch(queryString, cb) {
                var links = this.links;
                var results = queryString ? links.filter(this.createFilter(queryString)) : links;
                // call callback function to return suggestions
                cb(results);
            },
            createFilter(queryString) {
                return (link) => {
                    return (link.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },
            loadAll() {
                return [
                    { "value": "vue", "link": "https://github.com/vuejs/vue" },
                    { "value": "element", "link": "https://github.com/ElemeFE/element" },
                    { "value": "cooking", "link": "https://github.com/ElemeFE/cooking" },
                    { "value": "mint-ui", "link": "https://github.com/ElemeFE/mint-ui" },
                    { "value": "vuex", "link": "https://github.com/vuejs/vuex" },
                    { "value": "vue-router", "link": "https://github.com/vuejs/vue-router" },
                    { "value": "babel", "link": "https://github.com/babel/babel" }
                ];
            },
            handleSelect(item) {
                console.log(item);
            }
        },
        mounted() {
            this.links = this.loadAll();
        },

        searchSource(name){

        }
    }
</script>