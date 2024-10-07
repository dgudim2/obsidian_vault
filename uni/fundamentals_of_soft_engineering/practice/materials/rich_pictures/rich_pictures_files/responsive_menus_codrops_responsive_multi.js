var _____WB$wombat$assign$function_____ = function(name) {return (self._wb_wombat && self._wb_wombat.local_init && self._wb_wombat.local_init(name)) || self[name]; };
if (!self.__WB_pmw) { self.__WB_pmw = function(obj) { this.__WB_source = obj; return this; } }
{
  let window = _____WB$wombat$assign$function_____("window");
  let self = _____WB$wombat$assign$function_____("self");
  let document = _____WB$wombat$assign$function_____("document");
  let location = _____WB$wombat$assign$function_____("location");
  let top = _____WB$wombat$assign$function_____("top");
  let parent = _____WB$wombat$assign$function_____("parent");
  let frames = _____WB$wombat$assign$function_____("frames");
  let opener = _____WB$wombat$assign$function_____("opener");

/**
 * @file
 * Integrate codrops' ResponsiveMultiLevelMenu library with Responsive Menus.
 */
(function ($) {
  Drupal.behaviors.responsive_menus_codrops_responsive_multi = {
    attach: function (context, settings) {
      settings.responsive_menus = settings.responsive_menus || {};
      var $windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
      $.each(settings.responsive_menus, function(ind, iteration) {
        if (iteration.responsive_menus_style != 'codrops_responsive_multi') {
          return true;
        }
        if (!iteration.selectors.length) {
          return;
        }
        // Only apply if window size is correct.  Runs once on page load.
        var $media_size = iteration.media_size || 768;
        if ($windowWidth <= $media_size) {
          // Call codrops ResponsiveMultiLevelMenu with our settings.
          $(iteration.selectors).once('responsive-menus-codrops-multi-menu', function() {
            $(this).prepend('<button class="dl-trigger">Open Menu</button>');
            // Removing other classes / IDs.
            $(this)
              .attr('class', 'dl-menuwrapper')
              .attr('id', 'dl-menu')
              .css('z-index', '999');
            // Find the parent ul.
            var $parent_ul = $(this).find('ul:not(.contextual-links)').first();
            $parent_ul
              .attr('class', 'dl-menu')
              .attr('id', 'rm-dl-menu')
              .find('li').removeAttr('id').removeAttr('class')
              .find('a').removeAttr('id').removeAttr('class');
            // Add submenu classes.
            $parent_ul.find('ul').each(function(subIndex, subMenu) {
              $(this).removeAttr('id').attr('class', 'dl-submenu');
              $subnav_link = $(this).parent('li').find('a').first()[0].outerHTML;
              $(this).prepend('<li>' + $subnav_link + '</li>');
            });
            // Call the ResponsiveMultiLevelMenu dlmenu function.
            $(this).dlmenu({
                animationClasses : {
                  classin : iteration.animation_in || 'dl-animate-in-1',
                  classout : iteration.animation_out || 'dl-animate-out-1'
                }
            });
          });
        }

      });

    }
  };
}(jQuery));


}
/*
     FILE ARCHIVED ON 15:04:33 Nov 25, 2022 AND RETRIEVED FROM THE
     INTERNET ARCHIVE ON 13:44:13 Oct 07, 2024.
     JAVASCRIPT APPENDED BY WAYBACK MACHINE, COPYRIGHT INTERNET ARCHIVE.

     ALL OTHER CONTENT MAY ALSO BE PROTECTED BY COPYRIGHT (17 U.S.C.
     SECTION 108(a)(3)).
*/
/*
playback timings (ms):
  captures_list: 0.648
  exclusion.robots: 0.027
  exclusion.robots.policy: 0.016
  esindex: 0.01
  cdx.remote: 10.884
  LoadShardBlock: 44.425 (3)
  PetaboxLoader3.datanode: 76.475 (5)
  load_resource: 195.27 (2)
  PetaboxLoader3.resolve: 156.86 (2)
*/