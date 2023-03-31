冒泡，捕获事件的event.path兼容问题
const path = event.path || (event.composedPath && event.composedPath());