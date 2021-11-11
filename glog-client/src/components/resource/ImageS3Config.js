import aws from 'aws-sdk';

export default async function handler(req, res) {
    aws.config.update({
        accessKeyId: process.env.REACT_APP_S3_UPLOAD_KEY,
        secretAccessKey: process.env.REACT_APP_S3_UPLOAD_SECRET,
        region: process.env.REACT_APP_S3_UPLOAD_REGION,
        signatureVersion: 'v4',
    });

    const s3 = new aws.S3();
    const post = await s3.createPresignedPost({
        Bucket: process.env.REACT_APP_S3_UPLOAD_BUCKET,
        Fields: {
            key: req.query.file,
        },
        Expires: 60, // seconds
        Conditions: [
            ['content-length-range', 0, 1048576], // up to 1 MB
        ],
    });

    res.status(200).json(post);
}