<template>
    <el-pagination
            class="pager"
            background
            layout="total, sizes, prev, pager, next, jumper"
            :page-sizes="pageSizes"
            :page-size="pageSize"
            :page-count="totalPages"
            :current-page="currentPage"
            :pager-count="pagerCount"
            @size-change="onSizeChange"
            @current-change="onCurrentChange"
            :total="totalElements">
    </el-pagination>
</template>

<script>
    export default {
        name: "myPagination",
        props: ['pageSize', 'currentPage', 'totalPages', 'totalElements'],
        data() {
            return {
                pagerCount: 5,
                pageSizes: [5, 10, 20, 30, 40]
            }
        },
        methods: {
            onCurrentChange(value) {
                this.$emit("onCurrentChange", value)
            },

            onSizeChange(value) {
                this.$emit("onSizeChange", value)
            }
        }
    }
</script>

<style scoped>
    .pager {
        text-align: center;
        color: black;
        text-decoration: none;
        padding-top: 15px;
        padding-bottom: 15px;
    }
</style>