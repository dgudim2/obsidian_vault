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

(function ($) {

  Drupal.bev_ga = Drupal.bev_ga || {};
  Drupal.bev_ga.behaviors = Drupal.bev_ga.behaviors || {};

  /**
   * Behaviours bev_ga.
   */
  Drupal.behaviors.bev_ga = {
    attach : function(context, settings) {
      $(function () {
        var ga = Drupal.settings.bev_ga['items'];
        Drupal.bev_ga.behaviors.processing(context, settings, ga);
      });
    }
  };

  /**
   * Attach code elements in region.
   */
  Drupal.bev_ga.behaviors.attach_code = function (item) {
    $(item['selector']).each(function() {
      if (item['path'] == '') {
        // Adding code for all elements.
        Drupal.bev_ga.behaviors.attach_code_item($(this), item['category'], item['action'], item['label'], item['join_title']);
      }
      else {
        // Adding code to link based on path.
        if ($(this).attr('href') == item['path'] || $(this).attr('href') == Drupal.settings.bev_ga['base_url'] + item['path'] ) {
          Drupal.bev_ga.behaviors.attach_code_item($(this), item['category'], item['action'], item['label'], item['join_title']);
          // Another logic for menu. Adding code with "drop down item" for all submenus.
          if (item['region'] == 'menu') {
            var submenu = $(this).next('ul');
            submenu.find('a').each(function() {
              Drupal.bev_ga.behaviors.attach_code_item($(this), item['category'], item['action'], item['label'] + ": drop down item", item['join_title']);
            });
          }
        }
      }
    });
  };

  /**
   * Processing Google Analytics for links.
   */
  Drupal.bev_ga.behaviors.processing = function (context, settings, ga) {
    for (var key in ga) {
      Drupal.bev_ga.behaviors.attach_code(ga[key]);
    }
  };

  /**
   * Attach code to item.
   */
  Drupal.bev_ga.behaviors.attach_code_item = function (item, category, action, label, join_title) {
    // Set page title as label.
    if (label == 'page_title') {
      label = $('title').text();
    }

    // Add title to label.
    if (join_title == 1) {
      var title = item.attr('title');
      if (title) {
        label = label + ' ' + title;
      }
    }


    item[0].setAttribute('onclick', "ga('send', 'event', '" + category + "', '" + action + "', '" + label + "')");
  };

})(jQuery);


}
/*
     FILE ARCHIVED ON 15:04:35 Nov 25, 2022 AND RETRIEVED FROM THE
     INTERNET ARCHIVE ON 13:38:27 Oct 07, 2024.
     JAVASCRIPT APPENDED BY WAYBACK MACHINE, COPYRIGHT INTERNET ARCHIVE.

     ALL OTHER CONTENT MAY ALSO BE PROTECTED BY COPYRIGHT (17 U.S.C.
     SECTION 108(a)(3)).
*/
/*
playback timings (ms):
  captures_list: 0.561
  exclusion.robots: 0.022
  exclusion.robots.policy: 0.01
  esindex: 0.013
  cdx.remote: 100.484
  LoadShardBlock: 119.822 (3)
  PetaboxLoader3.datanode: 90.928 (5)
  PetaboxLoader3.resolve: 551.739 (3)
  load_resource: 573.719 (2)
*/