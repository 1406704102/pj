// function chooseImg(count, success) {
//     console.log(count)
//     uni.chooseImage({
//         count: count,
//         success: res => {
//             uploadFiles(res.tempFilePaths, success)
//         }
//     })
// }
// function chooseVideo(count, success) {
//     console.log(count)
//     uni.chooseVideo({
//         count: count,
//         success: res => {
//             uploadFiles(res.tempFilePath, success)
//         }
//     })
// }

import operate from "../common/operate";
import api from "../common/api";

async function uploadFiles(paths, type, success) {
    uni.showLoading({
        title: '正在上传'
    })
    for (let path of paths) {
        try {
            const uploadData = await uploadFile(path, type);
            success(uploadData)
        } catch (err) {
            console.log(err)
            uni.showToast({
                title: err || '上传失败',
                icon: 'error',
                mask: true
            })
            break;
        }
    }
    uni.hideLoading()
}

function uploadFile(path, type) {
    console.log(path)
    console.log(operate.api + api.updateVideo)
    return new Promise((resolve, reject) => {
        uni.uploadFile({
            url: type === 'image' ? operate.api + api.updateImage : operate.api + api.updateVideo,
            filePath: path,
            name: 'file',
            fileType: type,
            success: res => {
                console.log('上传文件', res)
                if (res.statusCode === 200) {
                    resolve(res.data)
                } else {
                    reject('上传失败');
                }
            },
            fail: () => {
                reject('网络错误');
            }
        });
    })
}

module.exports = {
    uploadFiles,
}
