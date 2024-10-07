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
 * A JavaScript file for the theme.
 *
 * In order for this JavaScript to be loaded on pages, see the instructions in
 * the README.txt next to this file.
 */

// JavaScript should be made compatible with libraries other than jQuery by
// wrapping it with an "anonymous closure". See:
// - https://drupal.org/node/1446420
// - http://www.adequatelygood.com/2010/3/JavaScript-Module-Pattern-In-Depth
(function ($, Drupal, window, document, undefined) {

  Drupal.beval = Drupal.beval || {};

  /**
   * Implementation for search popup.
   */
  Drupal.behaviors.beval_search = {
    attach: function (context, settings) {
      // Define variables.
      var block_search_form = $('#block-search-form form');
      var content_type_selectbox = block_search_form.find('.custom-search-types').hide();
      var content_type_filter = $('#search-filter-content-type').hide();
      var content_type_filter_switcher = $('#search-filter-switcher');
      var search_box = block_search_form.find('.custom-search-box');

      // Hide popup when user click on another element.
      $('body').click(function (event) {
        if ($(event.target).closest('#search-filter-switcher').length === 0
          && $(event.target).closest('#search-filter-content-type').length === 0
          && $(event.target).closest('.custom-search-default-value').length === 0) {
          content_type_filter.hide();
          content_type_filter_switcher.removeClass('active');
          $('.form-error-popup').remove();
        }
      });

      // Toggle popup on click on switcher.
      content_type_filter_switcher.click(function() {
        content_type_filter.toggle();
        content_type_filter_switcher.toggleClass('active');
      });

      // Filter on click.
      content_type_filter.find('a').each(function() {
        $(this).click(function(event) {
          var content_type = $(this).data('content-type');
          // If filter members, redirect user to the user search.
          if (content_type == 'members') {
            var keyword = search_box.val();
            if (keyword == '') {
              search_box.addClass('error');
              Drupal.beval.show_search_error(block_search_form);
            }
            else {
              search_box.removeClass('error');
              window.location = '/search/user/' + keyword;
            }
          }
          else if (keyword == '') {
            Drupal.beval.show_search_error(block_search_form);
          }
          else {
            // Set value to hidden select box.
            content_type_selectbox
              .attr('selected', '')
              .find('[value="' + content_type + '"]')
              .attr('selected', 'selected');
            block_search_form.submit();
          }
          event.preventDefault();
        });
      });

      block_search_form.submit(function(event) {
        var keyword = search_box.val();
        if (keyword == '') {
          Drupal.beval.show_search_error(block_search_form);
          event.preventDefault();
          return;
        }

        var content_type = $('#block-search-form form .custom-search-types').find(":selected").val().substr('2');
        if (content_type !== undefined && content_type !== 'all') {
          event.preventDefault();
          window.location = Drupal.settings.basePath + "search/site/" + keyword + '?f[0]=bundle:' + content_type;
        }
      });
    }
  };

  /**
   * Display error for search.
   */
  Drupal.beval.show_search_error = function (element) {
    if ($('.form-error-popup').length == 0) {
      element.before('<div class="form-error-popup"/>').prev().text(Drupal.t('You need to enter a keyword'));
    }
  };

  /**
   * Improves height for featured block tabs.
   */
  Drupal.behaviors.beval_featured_tabs = {
    attach: function (context, settings) {
      Drupal.beval.featured_equal_heights();
      $(window).resize(function() {
        Drupal.beval.featured_equal_heights();
      });
    }
  };

  /**
   * Improves main menu.
   */
  Drupal.behaviors.beval_main_menu = {
    attach: function (context, settings) {
      var main_menu_wrapper = $('#block-system-main-menu');
      // Adding delimiters.
      main_menu_wrapper
        .find('ul:first > li:not(:last-child):not(.main-menu-delimiter):not(.processed)')
        .addClass('processed')
        .after('<li class="main-menu-delimiter"/>');

      // Hide delimiters on hover.
      main_menu_wrapper
        .find('ul')
        .children('li:not(:first-child)').hover(function() {
          $(this).prev().addClass('active-delimiter');
          $(this).next().addClass('active-delimiter');
        },
        function() {
          $(this).prev().removeClass('active-delimiter');
          $(this).next().removeClass('active-delimiter');
        });

      // Group blocks in rainbow menu.
      main_menu_wrapper.find('li.evaluation_options > ul.menu > li:lt(3)').once().wrapAll('<div class="column first" />');
      main_menu_wrapper.find('li.evaluation_options > ul.menu > li').once().wrapAll('<div class="column second" />');

      // Teaser dropdown menus.
      main_menu_wrapper
        .find('li.is-teaser-dropdown > .menu').wrapAll('<div class="menu-teaser__wrapper" />');

      main_menu_wrapper.find('.menu-teaser__wrapper').each(function(index) {
        var teaserWrapper = this,
            teasers = $(teaserWrapper).find('.menu-teaser__teaser--child'),
            links = $(this).find('ul.menu > li');

        links.on('mouseenter', function() {
          var activeMlid = $(this).data('mlid');
          teasers.each(function() {
            var mlid = $(this).data('mlid')
            if (activeMlid === mlid) {
              $(this).addClass('is-active')
            }
            else {
              $(this).removeClass('is-active')
            }
          });
        });
      });
    }
  };

  /**
   * Improves contact form.
   */
  Drupal.behaviors.beval_contact_form = {
    attach: function (context, settings) {
      var contact_form = $('#contact-site-form');
      var captcha = contact_form.find('#edit-captcha-form').once().hide();
      contact_form.find('input, textarea').focus(function() {
        captcha.show();
      });
    }
  };

  /**
   * Improves comment form.
   */
  Drupal.behaviors.beval_comment_form = {
    attach: function (context, settings) {
      $(document).ready(function() {
        if (typeof(CKEDITOR) !== 'undefined') {
          var comment_form = $('#comment-form');
          var captcha = comment_form.find('.form-item-mollom-captcha').once().hide();
          if (CKEDITOR.instances['edit-comment-body-und-0-value']) {
            var ckeditor = CKEDITOR.instances['edit-comment-body-und-0-value'];
            ckeditor.on('focus', function() {
              captcha.show();
            });
          }
          // For mobile
          var textarea = comment_form.find('textarea');
          textarea.on('focus', function() {
            captcha.show();
          });
        }
      });
    }
  };

  /**
   * Activate jQuery placeholders to work in IE.
   */
  Drupal.behaviors.beval_placeholders = {
    attach: function (context, settings) {
      $('input, textarea').placeholder();
    }
  };

  /**
   * Make equal height for featured tabs.
   */
  Drupal.beval.featured_equal_heights = function() {
    var featured_block = $('#block-views-featured-content-block');
    var featured_tabs = featured_block.find('.views-slideshow-pager-field-item');
    var max_height = 0;

    // Calculate max height of image.
    featured_block.find('.views-slideshow-cycle-main-frame-row img').each(function () {
      if (max_height < $(this).attr('height')) {
        max_height = $(this).attr('height');
      }
    });

    // Require jquery.equalheights.js.
    var padding_top = parseInt(featured_tabs.find('.views-field-field-carousel-title').css('padding-top'));
    var padding_bottom = parseInt(featured_tabs.find('.views-field-field-carousel-title').css('padding-bottom'));
    var border_top = parseInt(featured_tabs.css('border-top-width'));
    var height = (max_height / featured_tabs.length) - (padding_top + padding_bottom + border_top);

    // Equal heights for all tabs.
    featured_tabs
      .find('.views-field-field-carousel-title')
      .equalHeights(height, height)
      .css('overflow', 'visible');

    // Vertical position for tabs content.
    featured_tabs
      .find('.views-content-field-carousel-title')
      .each(function() {
        $(this).valignMiddle();
      });
  };


  /**
   * Comission
   */
  Drupal.behaviors.beval_commission = {
    attach: function (context, settings) {
      var ct  = $(context).find('.bcg-container'),
          full = ct.find('.show-full');

      full.click(function(e){
        e.preventDefault();
        ct.find('.step-container').show();
        ct.find('.step-container .opener').show();
        ct.find('.step-container li').show();
        ct.find('.step-container ul').hide();
        ct.find('.step-container.active ul').show();
      });

      ct.find('.step-container .opener').click(function(e){
        e.preventDefault();
        var  $this = $(this),
        $container = $this.parent().parent();

        if($this.hasClass('open')){
          $container.find('ul').hide();
        }else{
          $container.find('ul').show();
        }

        $this.toggleClass('open')

      });


    }
  };

  /**
   * Registration form
   */
  Drupal.behaviors.beval_registration = {
    attach: function (context, settings) {
      if($('.page-user-register').size() > 0 || $('.page-user-edit').size() > 0){
        var check_selector = '#edit-profile-focus-monitoring,#edit-profile-focus-capacity-strengthening,#edit-profile-commission,#edit-profile-focus-user,#edit-profile-focus-student,#edit-profile-focus-other,#edit-profile-interests-other';
        $(check_selector).change(function(){
          if($(this).is(':checked')){
            var $next = $(this).parent().next();
            if($next.find('select').size() > 0){
              $next.find('select option').first().text('How long have you been doing this?');
            }
            $next.show();
          }else{
            $(this).parent().next().hide();
          }
        });

        $(check_selector).change();


        $('#edit-profile-how-did-you-find').change(function(){
          var val = $(this).val();
          $('.form-item-profile-how-did-you-find-other').hide();
          if(val == 'Conference (specify)' || val == 'Social media (specify)' || val == 'Other (specify)'){
              $('.form-item-profile-how-did-you-find-other').show();
          }
        });

        var select_selector = '#edit-profile-allow-fullname';
        $(select_selector).each(function(i, select){
            var $select = $(select);
            $(select).prev().addClass('mainlabel').html('Display Full Name on my profile <span style="color:red;">*</span>');
            if($('.page-user-register').size() > 0) {
              $select.find('option').each(function(j, option){
                  var $option = $(option);
                  if($option.attr('value') == 0)return;
                  // Create a radio:
                  var $radio = $('<input type="radio" />');
                  // Set name and value:
                  $radio.attr('name', $select.attr('name')).attr('id', $select.attr('name') + '_' + $option.val()).attr('value', $option.val());
                  //Set required if the option was selected.
                  if ($option.attr('selected')) $radio.attr('required');
                  if($option.val() == 'Yes'){
                    $radio.prop('required',true);
                  }

                  // Insert radio before select box:
                  $select.before($radio);
                  // Insert a label:
                  $select.before(
                    $("<label />").attr('for', $select.attr('name') + '_' + $option.val()).text($option.text())
                  );
              });
              $select.remove();
            }
        });

        var select_selector = '#edit-profile-allow-city,#edit-profile-allow-country,#edit-profile-allow-jobtitle,#edit-profile-allow-orgtype,#edit-profile-allow-orgname,#edit-profile-allow-orgurl,#edit-profile-allow-mainfocus,#edit-profile-allow-mainarea,#edit-profile-allow-maininterest,#edit-profile-allow-website,#edit-profile-allow-twitter,#edit-profile-allow-skype,#edit-profile-allow-linkedin,#edit-profile-allow-other,#edit-profile-allow-interests';
        $(select_selector).each(function(i, select){
            var $select = $(select);
            if($('.page-user-register').size() > 0) {
              $select.find('option').each(function(j, option){
                  var $option = $(option);
                  if($option.attr('value') == 0)return;
                  // Create a radio:
                  var $radio = $('<input type="radio" />');
                  // Set name and value:
                  $radio.attr('name', $select.attr('name')).attr('id', $select.attr('name') + '_' + $option.val()).attr('value', $option.val());
                  //Set required if the option was selected.
                  if ($option.attr('selected')) $radio.attr('required');
                  if($option.val() == 'Yes'){
                    $radio.prop('required',true);
                  }

                  // Insert radio before select box:
                  $select.before($radio);
                  // Insert a label:
                  $select.before(
                    $("<label />").attr('for', $select.attr('name') + '_' + $option.val()).text($option.text())
                  );
              });
              $select.remove();
            }
        });



        document.getElementById("edit-mailchimp-lists-mailchimp-email-subscription-subscribe").checked = false;

      }
    }
  };

  Drupal.behaviors.frontpageSlider = {
    attach: function (context, settings) {
      var viewSelector = '#block-views-2020-frontpage-slide-block';
      var tabSelector = viewSelector + ' .view-display-id-button_block > .view-content';
      var hasSlider = !!$(context).find(viewSelector).length;
      if (!hasSlider) {
        return;
      }
      setActiveSlide(0);

      $(tabSelector).children().each(function(i, el) {
        $(el).click(function(event) {
          setActiveSlide(i);
        });
      });

      function setActiveSlide(index) {
        $(viewSelector).attr('data-active-slide', index + 1);
      }

    }
  };

  /**
   * Make a vertical position for an element in the middle.
   */
  $.fn.valignMiddle = function() {
    var elements_height = $(this).height();
    var parent_elements_height = $(this).parent().height();
    var padding = (parent_elements_height - elements_height) / 2;
    $(this).css('padding-top', padding);
    $(this).css('padding-bottom', padding);
  }

})(jQuery, Drupal, this, this.document);

/**
 * This code uses in the Related content view to hide "previous" and "next" buttons.
 */
/*var index = options.currSlide;
$('.view-related-content .views_slideshow_controls_text_previous')[index == 0 ? 'hide' : 'show']();
$('.view-related-content .views_slideshow_controls_text_next')[index == options.slideCount - 1 ? 'hide' : 'show']();*/


}
/*
     FILE ARCHIVED ON 15:04:37 Nov 25, 2022 AND RETRIEVED FROM THE
     INTERNET ARCHIVE ON 13:44:18 Oct 07, 2024.
     JAVASCRIPT APPENDED BY WAYBACK MACHINE, COPYRIGHT INTERNET ARCHIVE.

     ALL OTHER CONTENT MAY ALSO BE PROTECTED BY COPYRIGHT (17 U.S.C.
     SECTION 108(a)(3)).
*/
/*
playback timings (ms):
  captures_list: 0.437
  exclusion.robots: 0.019
  exclusion.robots.policy: 0.012
  esindex: 0.008
  cdx.remote: 4.594
  LoadShardBlock: 46.445 (3)
  PetaboxLoader3.datanode: 62.51 (4)
  load_resource: 268.396
  PetaboxLoader3.resolve: 181.771
*/