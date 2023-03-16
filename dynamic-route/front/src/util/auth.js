const accessTokenKey = 'ACCESS_TOKEN'

export function getToken() {
  return window.localStorage.getItem(accessTokenKey)
}

export function setToken(token) {
  window.localStorage.setItem(accessTokenKey, token)
}

export function removeToken() {
  return window.localStorage.removeItem(accessTokenKey)
}
