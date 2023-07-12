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
import TcVod from 'vod-js-sdk-v6'


async function uploadFiles(paths, type, signature, success) {
    uni.showLoading({
        title: '正在上传'
    })


    const tcVod = new TcVod({
        getSignature: () => {
            return signature;
        }
    })
    for (let path of paths) {
        try {
            const uploadData = await uploadFile(tcVod,path, type, signature);
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

function uploadFile(tcVod,path, type, signature) {

    console.log(signature)
    console.log(operate.api + api.updateVideo)
    return new Promise((resolve, reject) => {

        //tcVod 上传 path是文件
        const uploader = tcVod.upload({
            mediaFile: path, // 媒体文件（视频或音频或图片），类型为 File
        })
        // uploader.on('media_progress', function (info) {
        //     console.log(info.percent) // 进度
        // });
        uploader.done().then(function (doneResult) {
            resolve(doneResult)
        }).catch(function (err) {
        })


        //uniapp 上传 path是文件路径
        // uni.uploadFile({
        //     url: type === 'image' ? operate.api + api.updateImage : operate.api + api.updateVideo,
        //     filePath: path,
        //     name: 'file',
        //     fileType: type,
        //     success: res => {
        //         console.log('上传文件', res)
        //         if (res.statusCode === 200) {
        //             resolve(JSON.parse(res.data));
        //         } else {
        //             reject('上传失败');
        //         }
        //     },
        //     fail: () => {
        //         reject('网络错误');
        //     }
        // });
    });
}

module.exports = {
    uploadFiles,
}
