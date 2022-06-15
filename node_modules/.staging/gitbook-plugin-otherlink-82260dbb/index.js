module.exports = {
    // website or book
    book: {
        assets: './assets',
        js: [
            './js/otherlink.js'
        ],
        // still not work!
        html: {
            'html:end': function() {
                return '<!-- End of book -->';
            }
        }
    },

    // very useful!
    blocks: {
        // {% otherlink %}content{% endotherlink %}
        otherlink: {
            process: function( block ) {
                return '<div style="color:red">' + block.body + '</div>';
            }
        }
    },

    hooks: {
        init: function() {
            // console.log( 'init!' );
        },

        finish: function() {
            // console.log( 'finish!' );
        }
    }
};
