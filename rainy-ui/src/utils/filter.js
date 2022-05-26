import Vue from 'vue'
import moment from 'moment'
import 'moment/locale/zh-cn'
import store from '@/store'

moment.locale('zh-cn')

Vue.filter('dictItems', function (dictCode) {
  const arr = []
  const dict = store.getters.dictTree[dictCode]
  if (!dict) {
    return arr
  }
  const type = dict.type
  const items = dict.items
  for (const item in items) {
    const name = items[item]
    let value = item
    if (type === 'int') {
      value = Number(value)
    }
    if (type === 'boolean') {
      // value = Boolean(value) // false|true 都会转换成 true
      value = value === 'true'
    }
    const nameVal = { name: name, value: value }
    arr.push(nameVal)
  }
  return arr
})

Vue.filter('dictItemValue', function (dictCode, dictItemCode) {
  const dict = store.getters.dictTree[dictCode]
  if (!dict) {
    return dictItemCode
  }
  const dictItem = dict.items[dictItemCode]
  if (!dictItem) {
    return dictItemCode
  }
  return dictItem
})

Vue.filter('NumberFormat', function (value) {
  if (!value) {
    return '0'
  }
  const intPartFormat = value.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') // 将整数部分逢三一断
  return intPartFormat
})

Vue.filter('dayjs', function (dataStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(dataStr).format(pattern)
})

Vue.filter('moment', function (dataStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(dataStr).format(pattern)
})
