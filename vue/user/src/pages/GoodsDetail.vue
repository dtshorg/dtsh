<template>
  <div class="goods" v-if="!isNotFound">
    <van-cell-group>
      <van-cell>
        <template #title>
          <van-image width="100%" :src="goods.image" />
          <div class="goods-price">
            {{ goods.point }}
            <span class="small">积分</span>
          </div>
          <div class="goods-title">
            <span class="small"> {{ goods.name }}</span>
          </div> 
        </template>
      </van-cell>
      <van-cell class="goods-express">
        <template #title>
          <van-col span="14">剩余：{{ goods.stock }}</van-col>
        </template>
      </van-cell>
    </van-cell-group>
    <div class="goods-cell-title">
      —— 商品详情 ——
    </div>
    <div class="goods-description" v-html="goods.description"></div>
    <van-action-bar>
      <van-action-bar-button type="danger" @click="onClick" text="立即兑换" />
    </van-action-bar>
  </div>
  <div class="goods-not-found" v-else>商品不存在</div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { getGoodsAlbum, getGoods, addBuy } from '../api'
import useUser from '../stores/user'

const { user } = useUser()

const props = defineProps({
  id: String
})

const goods = reactive({})
const isNotFound = ref(false)

onMounted(() => {
  loadGoodsDetail()
})

// 加载商品详情
const loadGoodsDetail = async () => {
  const data = await getGoods({ id: props.id })
  if (!data.id) {
    isNotFound.value = true
    return
  }
  Object.assign(goods, data)
}


import { showToast, showSuccessToast } from 'vant'
import { useRouter } from 'vue-router'

const router = useRouter()

const onClick = async () => {
  const data = await addBuy({
    userId: user.id,
    goodsId: props.id
  })
  if (data == 1) {
    showSuccessToast('兑换成功');
  }
}

</script>

<style lang="less" scoped>
.goods {
  text-align: center;
  padding-bottom: 50px;
  .goods-swipe {
    img {
      width: 100%;
      display: block;
    }
  }
  .goods-top {
    display: block;
    width: 30px;
    padding: 0 5px;
    border-radius: 10px;
    color: #fff;
    background-color: #f44;
  }
  .goods-title {
    text-align: left;
    .small {
      font-size: 14px;
    }
  }
  .goods-price {
    color: #f44;
    text-align: left;
    font-size: 20px;
    .small {
      font-size: 12px;
    }
    .spec {
      font-size: 12px;
      color: #999;
    }
  }
  .goods-cell-title {
    padding: 20px 0;
  }
  .goods-description {
    width: 95%;
    margin: 0 auto;
    padding-bottom: 20px;
    font-size: 14px;
    :deep(img) {
      max-width: 100%;
      height: auto;
      display: block;
    }
  }
  &-express {
    color: #999;
    font-size: 12px;
    padding: 5px 15px;
    :deep(.van-col) {
      float: left;
    }
  }
 .goods-cell-group {
    :deep(.van-cell__title span){
      float: left;
    }
  }
  :deep(.van-cell:after) {
    border: none;
  }
}
.goods-not-found {
  padding-top: 48px;
  text-align: center;
  font-size: 28px;
}
</style>
