require( [ 'gitbook', 'jquery' ], function( gitbook, $ ) {

    function fixSummaryLinks() {
        var $links = $( '.book-summary nav li a' );
        var bookRoot = gitbook.state.bookRoot;

        if ( !/\/$/.test( bookRoot ) ) {
            bookRoot += '/';
        } 
        $links.each( function( index, link ) {
            var $link = $( link ), href = $link.attr( 'href' );
            if ( /^ref:\/\//.test( href ) ) {
                $link.attr( 
                    'href'
                    // All relative links in summary.md is also relative to bookRoot
                    , bookRoot + href.replace( /^ref:\/\//, '' ) 
                );
            }
        } );
    }

    function fixPageLinks() {
        var $links = $( '.book-body a' );
        $links.each( function( index, link ) {
            var $link = $( link ), href = $link.attr( 'href' );
            if ( /^ref:\/\//.test( href ) ) {
                $link.attr( 
                    'href'
                    , href.replace( /^ref:\/\//, '' ) 
                );
            }
        } );
    }

    // invocation on pageready 
    gitbook.push( function() {
        // gitbook.state is now ready
        fixSummaryLinks();
        fixPageLinks();
    } );

    // page.change invocation
    gitbook.events.on( 'page.change', function() {
        // console.log( gitbook );
        fixSummaryLinks();
        fixPageLinks();
    } );

} );
